package me.androidbox.peopledb.peoplelist;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import me.androidbox.peopledb.di.DaggerInjector;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 10/29/16.
 */

public class PeopleListPresenterImp implements
        PeopleListPresenterContract<PeopleView>,
        PeopleListModelContract.DeleteListener,
        PeopleListModelContract.InsertIntoDBListener,
        PeopleListModelContract.LoadPersonListener,
        PeopleListModelContract.UpdateDBListener{

    private PeopleView mPeopleView;

    @Inject PeopleListModelImp mPeopleListModel;

    public PeopleListPresenterImp() {
        DaggerInjector.getAppComponent().inject(PeopleListPresenterImp.this);
    }

    @Override
    public void attachView(PeopleView peopleView) {
        mPeopleView = peopleView;
    }

    @Override
    public void detachView() {
        mPeopleView = null;
        mPeopleListModel.releaseResources();
    }

    /** Model <<-- Presenter */
    @Override
    public void deletePersons() {

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
    public void updatePerson() {

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
    public void onDeleteSuccess() {
        Timber.d("onDeleteSuccess");
        mPeopleView.deletionSuccess();
    }

    @Override
    public void onInsertSuccess() {
        Timber.d("onInsertSuccess");
        mPeopleView.insertionSuccess();
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
    public void onLoadPersonFailure() {
        Timber.e("onLoadPersonFailure");
        mPeopleView.loadFailure();
    }

    @Override
    public void onUpdateSuccess() {
        Timber.d("onUpdateSuccess");
        mPeopleView.updateSuccess();
    }

    @Override
    public void onUpdateFailure(String errMessage) {
        Timber.e("onUpdateFailure %s", errMessage);
        mPeopleView.updateFailure();
    }
}
