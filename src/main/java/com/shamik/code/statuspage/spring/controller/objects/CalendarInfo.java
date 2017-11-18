package com.shamik.code.statuspage.spring.controller.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 11/17/17.
 */

@Entity
public class CalendarInfo
{
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

    String calendarUrl;
}
