package com.shamik.code.statuspage.spring.controller.objects;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 2/19/18.
 */

@Entity
public class GoogleAuthInfo {


    String redirectUri;

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(notes = "The first name of the user as listed on their Google Profile")
    @Id
    String name;


}
