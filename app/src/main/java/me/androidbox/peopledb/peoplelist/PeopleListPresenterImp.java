package me.androidbox.peopledb.peoplelist;

import java.util.List;

import javax.inject.Inject;

import me.androidbox.peopledb.di.DaggerInjector;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 10/29/16.
 */

public class PeopleListPresenterImp implements
        PeopleListPresenterContract<PeopleListView>,
        PeopleListModelContract.DeleteListener,
        PeopleListModelContract.InsertIntoDBListener,
        PeopleListModelContract.LoadPersonListener,
        PeopleListModelContract.UpdateDBListener{

    private PeopleListView mPeopleView;

    @Inject PeopleListModelImp mPeopleListModel;

    public PeopleListPresenterImp() {
        DaggerInjector.getAppComponent().inject(PeopleListPresenterImp.this);
    }

    @Override
    public void attachView(PeopleListView peopleView) {
        mPeopleView = peopleView;
    }

    @Override
    public void detachView() {
        mPeopleView = null;
        mPeopleListModel.releaseResources();
    }

    /** Model <<-- Presenter */
    @Override
    public void deletePersons(Person person) {
        mPeopleListModel.deletePerson(person, PeopleListPresenterImp.this);
    }

    @Override
    public void insertPerson() {
        Person person = new Person();
        person.setFirstName(mPeopleView.getFirstName());
        person.setLastName(mPeopleView.getLastName());
        person.setPhoneNumber(mPeopleView.getPhoneNumber());
        person.setDob(mPeopleView.getDob());
        person.setZip(mPeopleView.getZip());

        mPeopleListModel.insertPersonInto(person, PeopleListPresenterImp.this);
    }

    @Override
    public void updatePerson(final Person person) {
        mPeopleListModel.updatePerson(person, PeopleListPresenterImp.this);
    }

    @Override
    public void loadPersons() {
        mPeopleListModel.loadPersons(PeopleListPresenterImp.this);
    }

    /** Model -->> Presenter */
    @Override
    public void onDeleteFailure(String errMessage) {
        Timber.e("onDeleteFailure: %s", errMessage);
        mPeopleView.deletionFailure();
    }

    @Override
    public void onDeleteSuccess(Person person) {
        Timber.d("onDeleteSuccess");
        mPeopleView.deletionSuccess(person);
    }

    @Override
    public void onInsertSuccess(Person person) {
        Timber.d("onInsertSuccess");
        mPeopleView.insertionSuccess(person);
    }

    @Override
    public void onInsertFailure(String errMessage) {
        Timber.e("onInsertFailure %s", errMessage);
        mPeopleView.insertionFailure();
    }

    @Override
    public void onLoadPersonSuccess(List<Person> personList) {
        Timber.d("onLoadPersonSuccess");
        mPeopleView.loadSuccess(personList);
    }

    @Override
    public void onLoadPersonFailure(String errMessage) {
        Timber.e("onLoadPersonFailure %s", errMessage);
        mPeopleView.loadFailure();
    }

    @Override
    public void onUpdateSuccess(Person person) {
        Timber.d("onUpdateSuccess");
        mPeopleView.updateSuccess(person);
    }

    @Override
    public void onUpdateFailure(String errMessage) {
        Timber.e("onUpdateFailure %s", errMessage);
        mPeopleView.updateFailure();
    }
}
