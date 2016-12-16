package com.example.bogda.geekhubandroidgrouplist.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bogda on 25.10.2016.
 */

public class People extends RealmObject implements Comparable {
    private String name;
    @PrimaryKey
    private String googlePlusId;
    private String gitHubUserName;

    public People(){}

    public People(String name, String googlePlusId, String gitHubUserName) {
        this.name = name;
        this.googlePlusId = googlePlusId;
        this.gitHubUserName = gitHubUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGooglePlusId() {
        return googlePlusId;
    }


    public String getGitHubUserName() {
        return gitHubUserName;
    }


    @Override
    public int compareTo(Object o) {
        People compPeople = (People) o;
        if (compPeople.getName().compareTo(getName()) >= 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
