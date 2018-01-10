package com.shamik.code.statuspage.spring.controller.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 11/15/17.
 */
@Entity
public class UserInfo
{

    public Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Id
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getPictureUrl()
    {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String name;

    public String firstName;

    public String familyName;

    public String link;

    public String pictureUrl;

    public String gender;

    public String refreshToken;

    public String getLastUsedAccessToken() {
        return lastUsedAccessToken;
    }

    public void setLastUsedAccessToken(String lastUsedAccessToken) {
        this.lastUsedAccessToken = lastUsedAccessToken;
    }

    public String lastUsedAccessToken;

    public String toString(){

        return "Name: " + name
                + "\nGiven Name: " + firstName
                + "\nLast Name:" + familyName
                + "\nRefresh Token:" + refreshToken + "\n";

    }

}
