package com.shamik.code.statuspage.spring.controller;

import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;


@RestController
public class StatusPageController {

    private static final Logger logger = LoggerFactory.getLogger(StatusPageController.class);


    @CrossOrigin
    @RequestMapping("/")
    public String index() {

        Random rand = new Random();

        int  n = rand.nextInt(50) + 1;

        String returnString = "greetings from spring boot - random number=" + n ;
        logger.debug("return message is " + returnString);

        return returnString;
    }

    @CrossOrigin
    @RequestMapping("/weather")
    public String weatherItem(){

        String returnData = "";

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("http://w1.weather.gov/xml/current_obs/KPAE.xml");

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("current_observation");

            Node nNode = nList.item(0);

            JSONObject obj = new JSONObject();

            if (nNode.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) nNode;

                String title = e.getElementsByTagName("temp_f").item(0).getTextContent();
                String desc = e.getElementsByTagName("weather").item(0).getTextContent();
                String iconUrl = e.getElementsByTagName("icon_url_base").item(0).getTextContent() + e.getElementsByTagName("icon_url_name").item(0).getTextContent();

                String windData = e.getElementsByTagName("wind_string").item(0).getTextContent() + " " + e.getElementsByTagName("wind_dir").item(0).getTextContent();

                String lastUpdated = e.getElementsByTagName("observation_time").item(0).getTextContent();

                obj.put("title", title);
                obj.put("desc", desc);
                obj.put("iconUrl", iconUrl);
                obj.put("windData", windData);
                obj.put("lastUpdated", lastUpdated);



                returnData = obj.toJSONString();

            }

        } catch (Exception ioe){
            return "there was an exception parsing the feed contents\n" + ioe.toString();
        }


        return returnData;


    }


    @CrossOrigin
    @RequestMapping("/news")
    public String newsItem(){

        String returnTitle = "";

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("http://feeds.feedburner.com/ndtvnews-top-stories");

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");

            Random rand = new Random();

            int  randomTitleElement = rand.nextInt(nList.getLength()) + 1;

            Node nNode = nList.item(randomTitleElement);

            JSONObject obj = new JSONObject();

            if (nNode.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) nNode;

                returnTitle = new String();
                String title = e.getElementsByTagName("title").item(0).getTextContent();
                String desc = e.getElementsByTagName("description").item(0).getTextContent();



                obj.put("headline", title);
                obj.put("description", desc);

                returnTitle = obj.toJSONString();


            }

        } catch (Exception ioe){
            return "there was an exception parsing the feed contents\n" + ioe;
        }


        return returnTitle;
    }

    @CrossOrigin
    @RequestMapping("/calendar")
    public String getCalendar(){


        return null;
    }

    @CrossOrigin
    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }


    @CrossOrigin
    @RequestMapping("/photos")
    public String getPhotosLink(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails currentPrincipalToken = (OAuth2AuthenticationDetails)auth.getDetails();


        logger.debug("current principal name is " + currentPrincipalToken.getTokenValue());

        String listOfPhotosXml =  "";

        String returnUrl = "";


        try{
            listOfPhotosXml = sendGet("https://picasaweb.google.com/data/feed/api/user/default/albumid/6480500567927330529?imgmax=1600u", currentPrincipalToken.getTokenValue());

            logger.debug("the photo xml is " + listOfPhotosXml);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(listOfPhotosXml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("entry");

            logger.debug("num of entries is " + nList.getLength());

            //for (int temp = 0; temp < nList.getLength(); temp++) {

            int randomNum = ThreadLocalRandom.current().nextInt(0, nList.getLength() + 1);

                Node nNode = nList.item(randomNum);



                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    logger.debug("Photo title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                    Element contentElement = (Element)eElement.getElementsByTagName("content").item(0);
                    logger.debug("URL:" + contentElement.getAttribute("src"));
                    returnUrl = contentElement.getAttribute("src");
                }

            //}



        }catch(Exception e){
            logger.debug("could not retrieve pictures at this time " + e.toString());
        }


        return "{ \"photoUrl\" : \"" + returnUrl + "\"}";
    }

    private String sendGet(String url, String token) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "StatusBoard v1.0");
        con.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = con.getResponseCode();
        logger.debug("\nSending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }



}