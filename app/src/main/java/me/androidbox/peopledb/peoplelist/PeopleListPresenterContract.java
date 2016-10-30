package me.androidbox.peopledb.peoplelist;

/**
 * Created by steve on 10/29/16.
 */

public interface PeopleListPresenterContract<PeopleView> {
    /** Attach and detach the view */
    void attachView(PeopleView view);
    void detachView();

    /** Presenter <<-- View */
    void insertPerson();
    void updatePerson();
    void loadPersons();
    void deletePersons();
}
