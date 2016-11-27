package com.example.bogda.geekhubandroidgrouplist.data.GooglePlusUser;

import java.util.List;

/**
 * Created by bohdan on 04.11.16.
 */

public class GooglePlusUser {
    private String birthday;
    private String gender;
    private String objectType;
    private String displayName;
    private String url;
    private Image image;
    private List<Organization> organizations;
    private List<Place> placesLived;
    private Cover cover;

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public Image getImage() {
        return image;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public List<Place> getPlacesLived() {
        return placesLived;
    }

    public Cover getCover() {
        return cover;
    }

    public String getObjectType() {
        return objectType;
    }
}
