package me.androidbox.peopledb.peoplelist;

import me.androidbox.peopledb.model.Person;

/**
 * Created by steve on 10/27/16.
 */

public interface PeopleListModelContract {
    interface InsertIntoDBListener {
        void onInsertSuccess();
        void onInsertFailure(String errMessage);
    }
    void insertPersonInto(Person person, InsertIntoDBListener insertIntoDBListener);

    interface UpdateDBListener {
        void onUpdateSuccess();
        void onUpdateFailure(String errMessage);
    }
    void updatePerson(UpdateDBListener updateDBListener);

    interface DeleteListener {
        void onDeleteSuccess();
        void onDeleteFailure(String errMessage);
    }
    void deletePerson(DeleteListener deleteListener);

    void releaseResources();
}
