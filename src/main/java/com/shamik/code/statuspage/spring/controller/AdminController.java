package com.shamik.code.statuspage.spring.controller;

import com.shamik.code.statuspage.spring.controller.objects.*;
import com.shamik.code.statuspage.spring.controller.repository.CalendarInfoRepository;
import com.shamik.code.statuspage.spring.controller.repository.GoogleAuthInfoRepository;
import com.shamik.code.statuspage.spring.controller.repository.PhotoInfoRepository;
import com.shamik.code.statuspage.spring.controller.repository.UserInfoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shamik.shah on 1/10/18.
 */

@Scope(value = "session")
@RestController
@RequestMapping("/rest")
@Api(value = "StatusPage Resources", description = "CRUD for StatusPage operations")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserInfoRepository ur;

    @Autowired
    CalendarInfoRepository cr;

    @Autowired
    PhotoInfoRepository pr;

    @Autowired
    GoogleAuthInfoRepository gr;



    @ApiOperation(value = "Adds a google oauth redirect URL for a user")
    @PostMapping("/googleConfig/addGoogleOauthConfig")
    public String addRecordToGoogleOAuth(@RequestBody final String body){

        JSONParser parser = new JSONParser();
        String redirectUrl;
        String name;

        try {

            JSONObject jsonObj = (JSONObject) parser.parse(body);

            name = (String) jsonObj.get("name");
            redirectUrl = (String) jsonObj.get("redirectUrl");

            GoogleAuthInfo gai = new GoogleAuthInfo();
            gai.setName(name);
            gai.setRedirectUri(redirectUrl);

            gr.save(gai);

        } catch (Exception e){
            e.printStackTrace();
            return "error google auth entry";
        }

        return "Successful";

    }




        @ApiOperation(value = "Returns all the Calendars in the system")
    @GetMapping("/calender/findAllCalendars")
    public List<CalendarInfo> getAllCalendarInfo()
    {
        return (List<CalendarInfo>) cr.findAll();
    }


    @ApiOperation(value = "Returns calendars for a specific user")
    @GetMapping("/calender/findByName")
    public List<CalendarInfo> getCalendarByName(@RequestParam("name") String name)
    {
        return (List<CalendarInfo>) cr.findByName(name);
    }


    @ApiOperation(value = "Returns all photo entries")
    @GetMapping("/photos/findAllPhotos")
    public List<PhotoInfo> getPhotoInfo(){
         return (List<PhotoInfo>) pr.findAll();

    }

    @ApiOperation(value = "Returns all photo entries for a user")
    @GetMapping("/photos/findByName")
    public List<PhotoInfo> getPhotoByName(@RequestParam("name") String name)
    {
        return (List<PhotoInfo>) pr.findByName(name);
    }

    @ApiOperation(value = "Adds a calendar for a user")
    @PostMapping("/calendar/addCalendar")
    public String addRecordToCalendar(@RequestBody final String body){

        JSONParser parser = new JSONParser();
        String name;
        String calendarUrl;

        try {
            logger.debug("token response from swagger is " + body);

            JSONObject jsonObj = (JSONObject) parser.parse(body);

            name = (String) jsonObj.get("name");
            calendarUrl = (String) jsonObj.get("calendar_url");

            System.out.print(name + "  " + name);

            CalendarInfo ci = new CalendarInfo();
            ci.setName(name);
            ci.setCalendarUrl(calendarUrl);

            cr.save(ci);

        } catch (Exception e){
            e.printStackTrace();
            return "error creating calendar entry";
        }

        return "Successful";
    }

    @ApiOperation(value = "Adds a photo album for a user")
    @PostMapping("/photos/addPhotoInfo")
    public String addPhotoInfo(@RequestBody final String body){

        JSONParser parser = new JSONParser();
        String peopleInPhotos;
        String albumId;
        String name;

        try {
            logger.debug("token response from swagger is " + body);

            JSONObject jsonObj = (JSONObject) parser.parse(body);

            name = (String) jsonObj.get("name");
            peopleInPhotos = (String) jsonObj.get("peopleInPhotos");
            albumId = (String) jsonObj.get("albumId");

            PhotoInfo pi = new PhotoInfo();
            pi.setName(name);
            pi.setAlbumId(albumId);
            pi.setPeopleInPhotos(peopleInPhotos);

            pr.save(pi);

        } catch (Exception e){
            e.printStackTrace();
            return "error creating photo entry";
        }

        return "Successful";
    }



}
