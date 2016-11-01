package me.androidbox.peopledb.peoplelist;

import me.androidbox.peopledb.model.Person;

/**
 * Created by steve on 10/29/16.
 */

public interface PeopleListPresenterContract<PeopleView> {
    /** Attach and detach the view */
    void attachView(PeopleView view);
    void detachView();

    /** Presenter <<-- View */
    void insertPerson();
    void updatePerson(Person person);
    void loadPersons();
    void deletePersons();
}
