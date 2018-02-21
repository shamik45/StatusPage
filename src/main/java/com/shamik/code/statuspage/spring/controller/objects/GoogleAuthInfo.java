package com.shamik.code.statuspage.spring.controller.objects;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 2/19/18.
 */

@Entity
public class GoogleAuthInfo {


    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @ApiModelProperty(notes = "The first name of the user as listed on their Google Profile")
    @Id
    String parameter;


}
