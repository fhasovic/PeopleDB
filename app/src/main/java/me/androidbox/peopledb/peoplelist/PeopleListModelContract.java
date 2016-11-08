package me.androidbox.peopledb.peoplelist;

import io.realm.RealmResults;
import me.androidbox.peopledb.model.Person;

/**
 * Created by steve on 10/27/16.
 */
public interface PeopleListModelContract {
    /** Insert a new user into the database */
    interface InsertIntoDBListener {
        void onInsertSuccess(Person person);
        void onInsertFailure(String errMessage);
    }
    void insertPersonInto(Person person, InsertIntoDBListener insertIntoDBListener);

    /** Update a existing user in a database */
    interface UpdateDBListener {
        void onUpdateSuccess(Person person);
        void onUpdateFailure(String errMessage);
    }
    void updatePerson(Person person, UpdateDBListener updateDBListener);

    /** Delete an existing person in the database */
    interface DeleteListener {
        void onDeleteSuccess(Person person);
        void onDeleteFailure(String errMessage);
    }
    void deletePerson(Person person, DeleteListener deleteListener);

    /** Load all users from the database */
    interface LoadPersonListener {
        void onLoadPersonSuccess(RealmResults<Person> personList);
        void onLoadPersonFailure(String errMessage);
    }
    void loadPersons(LoadPersonListener loadPersonListener);

    /** Release all resource to avoid any memory leaks */
    void releaseResources();

    /** Initialize resources */
    void initializeResources();
}
