package com.shamik.code.statuspage.spring.controller.objects;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 11/17/17.
 */
@Entity
public class PhotoInfo
{
    @ApiModelProperty(notes = "Comma Separated values of search terms to select photos from the album")
    String peopleInPhotos;

    public String getPeopleInPhotos()
    {
        return peopleInPhotos;
    }

    public void setPeopleInPhotos(String peopleInPhotos)
    {
        this.peopleInPhotos = peopleInPhotos;
    }

    public String getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(String albumId)
    {
        this.albumId = albumId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ApiModelProperty(notes = "The URL of the photo album")
    String albumId;

    @ApiModelProperty(notes = "The first name of the user as listed on their Google Profile")
    @Id
    String name;

    Boolean slateMode;

    public Boolean getSlateMode() {
        return slateMode;
    }

    public void setSlateMode(Boolean slateMode) {
        this.slateMode = slateMode;
    }

    public String getSlateLocation() {
        return slateLocation;
    }

    public void setSlateLocation(String slateLocation) {
        this.slateLocation = slateLocation;
    }

    String slateLocation;

}
