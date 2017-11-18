package com.shamik.code.statuspage.spring.controller.objects;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shamik.shah on 11/17/17.
 */
@Entity
public class PhotoInfo
{
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

    String albumId;

    @Id
    String name;

}
