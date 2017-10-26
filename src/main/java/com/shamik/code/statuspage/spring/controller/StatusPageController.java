package com.shamik.code.statuspage.spring.controller;

import com.shamik.code.statuspage.spring.controller.objects.CalendarEntry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;


@RestController
public class StatusPageController {

    private static final Logger logger = LoggerFactory.getLogger(StatusPageController.class);

    @Value("${weather.poll.url}")
    private String weatherPollUrl;

    @Value("${news.feed.url.list}")
    private String[] listOfFeedUrls;

    @Value("${calendar.url}")
    private String calendarUrl;

    @Value("${photos.url}")
    private String photosUrl;

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
    @RequestMapping("/calendar")
    public String getCalendar(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails currentPrincipalToken = (OAuth2AuthenticationDetails)auth.getDetails();

        JSONArray resultJson = new JSONArray();

        Hashtable resultMap = new Hashtable<String, List<CalendarEntry>>();

        JSONObject returnJson = new JSONObject();
        JSONArray listOfDays = new JSONArray();


        try{
            String calendarResponse = sendGet(calendarUrl,
                    currentPrincipalToken.getTokenValue());

            //logger.debug("the calendar response is " + calendarResponse);

            JSONParser parser = new JSONParser();

            JSONObject jsonObj = (JSONObject) parser.parse(calendarResponse);

            JSONArray items = (JSONArray) jsonObj.get("items");

            Iterator<JSONObject> itemIterator = items.iterator();




            while(itemIterator.hasNext()){

                JSONObject resultJsonItem = new JSONObject();

                CalendarEntry ce = new CalendarEntry();


                JSONObject itemObject = (JSONObject) itemIterator.next();
                JSONObject startDateTime = (JSONObject)itemObject.get("start");
                String startDate = ((String) startDateTime.get("dateTime")).split("T")[0].split("-")[2];
                //String startTime =  ((String) startDateTime.get("dateTime")).split("T")[1].split("-")[0];
                //String summary = (String) itemObject.get("summary");
                //String status = (String) itemObject.get("status");

                ce.setStatus((String) itemObject.get("status"));
                ce.setSummary((String) itemObject.get("summary"));
                ce.setStartTime(((String) startDateTime.get("dateTime")).split("T")[1].split("-")[0]);

                logger.debug("start dates are" + startDate);


                //resultJsonItem.put("day", startDate);
                //resultJsonItem.put("summary", summary);
                //resultJsonItem.put("status", status);

                //resultJson.add(resultJsonItem);

                //resultMap.put(startDate,summary);

                List<CalendarEntry> list = (List<CalendarEntry>)resultMap.get(startDate);
                if(list == null){
                    list = new ArrayList<CalendarEntry>();
                    resultMap.put(startDate,list);
                }
                list.add(ce);
            }

            //create the JSON feed
            //Iterator<String> it = resultMap.entrySet().iterator();

            Set<String> keys = resultMap.keySet();

            for(String key: keys){
                //String key = (String) it.next();
                List<CalendarEntry> listCe = (List<CalendarEntry>)resultMap.get(key);

                JSONObject toplevel = new JSONObject();
                toplevel.put("day", key);
                JSONArray listOfEntries = new JSONArray();

                Iterator listIter = listCe.iterator();
                while(listIter.hasNext()){
                    CalendarEntry ce = (CalendarEntry)listIter.next();
                    JSONObject entryObject = new JSONObject();
                    entryObject.put("status", ce.getStatus());
                    entryObject.put("startTime", ce.getStartTime());
                    entryObject.put("endTime", ce.getEndTime());
                    entryObject.put("summary", ce.getSummary());

                    listOfEntries.add(entryObject);
                }
                toplevel.put("events", listOfEntries);
                listOfDays.add(toplevel);
            }

            //logger.debug("result map is " + resultMap.toString());


        } catch (Exception e){
            logger.debug("Error in obtaining calendar feed ");
            e.printStackTrace();
        }

        returnJson.put("dayEntries", listOfDays);
        return returnJson.toJSONString();
    }


    @CrossOrigin
    @RequestMapping("/weather")
    public String weatherItem(){

        String returnData = "";

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //Document doc = dBuilder.parse("http://w1.weather.gov/xml/current_obs/KPAE.xml");
            Document doc = dBuilder.parse(weatherPollUrl);

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

        //logger.debug("news url is " + listOfFeedUrls[0]);
        //logger.debug("news url is " + listOfFeedUrls[1]);

        logger.debug("length of array " + listOfFeedUrls.length);

        try
        {
            //choose a feed at random
            int randomNum = ThreadLocalRandom.current().nextInt(0, listOfFeedUrls.length);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(listOfFeedUrls[randomNum]);

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
                obj.put("feedData", listOfFeedUrls[randomNum]);

                returnTitle = obj.toJSONString();


            }

        } catch (Exception ioe){
            return "there was an exception parsing the feed contents\n" + ioe;
        }


        return returnTitle;
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


        //logger.debug("current principal name is " + currentPrincipalToken.getTokenValue());

        String listOfPhotosXml =  "";

        String returnUrl = "";


        try{
            listOfPhotosXml = sendGet(photosUrl, currentPrincipalToken.getTokenValue());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(listOfPhotosXml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("entry");

            //logger.debug("num of entries is " + nList.getLength());

            //for (int temp = 0; temp < nList.getLength(); temp++) {

            int randomNum = ThreadLocalRandom.current().nextInt(0, nList.getLength() + 1);

                Node nNode = nList.item(randomNum);



                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    //logger.debug("Photo title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                    Element contentElement = (Element)eElement.getElementsByTagName("content").item(0);
                    //logger.debug("URL:" + contentElement.getAttribute("src"));
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