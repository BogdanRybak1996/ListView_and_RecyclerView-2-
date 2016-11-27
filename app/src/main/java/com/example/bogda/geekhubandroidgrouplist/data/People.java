package com.example.bogda.geekhubandroidgrouplist.data;

/**
 * Created by bogda on 25.10.2016.
 */

public class People implements Comparable {
    private String name;
    private String googlePlusId;
    private String gitHubUserName;

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

    public void setGooglePlusId(String googlePlusId) {
        this.googlePlusId = googlePlusId;
    }

    public String getGitHubUserName() {
        return gitHubUserName;
    }

    public void setGitHubUserName(String gitHubUserName) {
        this.gitHubUserName = gitHubUserName;
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
