package me.androidbox.peopledb.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by steve on 10/27/16.
 */
@Parcel(value = Parcel.Serialization.BEAN,
        analyze = {Person.class})
public class Person extends RealmObject {
    @PrimaryKey
    String mId;
    String mFirstName;
    String mLastName;
    String mPhoneNumber;
    String mDob;
    String mZip;

    public Person() {
    }

    public Person(String mDob, String mFirstName, String mId, String mLastName, String mPhoneNumber, String mZip) {
        this.mDob = mDob;
        this.mFirstName = mFirstName;
        this.mId = mId;
        this.mLastName = mLastName;
        this.mPhoneNumber = mPhoneNumber;
        this.mZip = mZip;
    }

    @Override
    public boolean isManaged() {
        return super.isManaged();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

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
