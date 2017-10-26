package com.shamik.code.statuspage.spring.controller.objects;

/**
 * Created by shamik.shah on 10/25/17.
 */
public class CalendarEntry
{
    String status;

    String startTime;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    String summary;

    String endTime;




}
