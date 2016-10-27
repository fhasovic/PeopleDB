package me.androidbox.peopledb.peoplelist;

import java.util.Map;

/**
 * Created by steve on 10/27/16.
 */

public interface PeopleListModelContract {
    interface InsertIntoDBListener {
        void onInsertSuccess();
        void onInsertFailure(String errMessage);
    }
    void insertPersonInto(InsertIntoDBListener insertIntoDBListener);

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
}
