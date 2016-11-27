package com.example.bogda.geekhubandroidgrouplist.data;

/**
 * Created by bohdan on 03.11.16.
 */

public class GitHubUser {
    private String login;
    private String avatar_url;
    private String type;
    private String name;
    private String location;
    private String email;
    private String bio;
    private int public_repos;
    private int followers;
    private int following;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
