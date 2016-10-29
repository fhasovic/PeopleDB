package me.androidbox.peopledb.peoplelist;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 10/27/16.
 */

public class PeopleListModelImp implements PeopleListModelContract {

    private Realm mRealm;

    public PeopleListModelImp() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void deletePerson(final Person person, final DeleteListener deleteListener) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Person> results = realm.where(Person.class).equalTo("mId", person.getId()).findAll();
                results.deleteFirstFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("onSuccess to delete person");
                deleteListener.onDeleteSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.d(error, "onError to delete person");
                deleteListener.onDeleteFailure(error.getMessage());
            }
        });
    }

    @Override
    public void insertPersonInto(final Person person, final InsertIntoDBListener insertIntoDBListener) {

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person personObject = realm.createObject(Person.class);
                personObject.setFirstName(person.getFirstName());
                personObject.setLastName(person.getLastName());
                personObject.setDob(person.getDob());
                personObject.setZip(person.getZip());
                personObject.setPhoneNumber(person.getPhoneNumber());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                insertIntoDBListener.onInsertSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.e(error, "Failed to create transaction");
                insertIntoDBListener.onInsertFailure(error.getMessage());
            }
        });
    }

    @Override
    public void updatePerson(final Person person, final UpdateDBListener updateDBListener) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(person);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                updateDBListener.onUpdateSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.e(error, "Failed to update person");
                updateDBListener.onUpdateFailure(error.getMessage());
            }
        });
    }

    @Override
    public void loadPersons(final LoadPersonListener loadPersonListener) {
        RealmResults<Person> persons = mRealm.where(Person.class).findAllAsync();

        persons.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {

            @Override
            public void onChange(RealmResults<Person> persons) {
                if(!persons.isEmpty()) {
                    Timber.d("Number of persons: %d", persons.size());
                    loadPersonListener.onLoadPersonSuccess(persons);
                }
                else {
                    Timber.d("No persons to load");
                    loadPersonListener.onLoadPersonFailure();
                }
            }
        });
    }

    @Override
    public void releaseResources() {
        if(!mRealm.isClosed()) {
            /* Cancel any pending transactions */
            if(mRealm.isInTransaction()) {
                mRealm.commitTransaction();
            }
            mRealm.close();
        }
    }
}

