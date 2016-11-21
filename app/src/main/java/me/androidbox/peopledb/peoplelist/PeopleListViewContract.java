package me.androidbox.peopledb.peoplelist;

import java.util.List;

import io.realm.RealmResults;
import me.androidbox.peopledb.model.Person;

/**
 * Created by steve on 10/29/16.
 */

public interface PeopleListViewContract {
    /** Presenter <<-- View */
    String getFirstName();
    String getLastName();
    String getDob();
    String getPhoneNumber();
    String getZip();

    /** Presenter -->> View */
    void insertionSuccess(Person person);
    void insertionFailure();
    void deletionSuccess(Person person);
    void deletionFailure();
    void updateSuccess(Person person);
    void updateFailure();
    void loadSuccess(List<Person> personList);
    void loadFailure();
}
