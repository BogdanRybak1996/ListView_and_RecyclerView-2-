package com.example.bogda.geekhubandroidgrouplist.data;

/**
 * Created by bogda on 27.11.2016.
 */

public class Contact implements Comparable {
    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Object o) {
        Contact compContact = (Contact) o;
        if (compContact.getName().compareTo(getName()) >= 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
