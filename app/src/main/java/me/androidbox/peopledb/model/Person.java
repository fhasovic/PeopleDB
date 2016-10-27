package me.androidbox.peopledb.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by steve on 10/27/16.
 */

public class Person extends RealmObject{
    @PrimaryKey
    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    private String mDob;
    private String mZip;

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        this.mDob = dob;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public String getZip() {
        return mZip;
    }

    public void setZip(String zip) {
        this.mZip = zip;
    }
}
