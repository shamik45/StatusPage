package com.shamik.code.statuspage.spring.controller.objects;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 11/17/17.
 */

@Entity
public class CalendarInfo
{
    @ApiModelProperty(notes = "The first name of the user as listed on their Google Profile")
    @Id
    String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCalendarUrl()
    {
        return calendarUrl;
    }

    public void setCalendarUrl(String calendarUrl)
    {
        this.calendarUrl = calendarUrl;
    }

    @ApiModelProperty(notes = "The URL of the calendar for which data will be displayed on the StatusPage")
    String calendarUrl;
}
