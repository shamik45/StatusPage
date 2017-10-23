package com.shamik.code.statuspage.spring.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.security.Principal;
import java.util.Random;


@RestController
public class StatusPageController {

    @CrossOrigin
    @RequestMapping("/")
    public String index() {

        Random rand = new Random();

        int  n = rand.nextInt(50) + 1;

        String returnString = "greetings from spring boot - random number=" + n ;

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


}