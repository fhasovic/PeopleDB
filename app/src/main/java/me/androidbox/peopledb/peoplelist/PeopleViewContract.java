package me.androidbox.peopledb.peoplelist;

/**
 * Created by steve on 10/29/16.
 */

public interface PeopleViewContract {
    void getFirstName();
    void getLastName();
    void getDob();
    void getPhoneNumber();
    void getZip();


    /** Presenter -->> View */
    void insertionSuccess();
    void insertionFailure();
    void deletionSuccess();
    void deletionFailure();
    void updateSuccess();
    void updateFailure();
    void loadSuccess();
    void loadFailure();
}
