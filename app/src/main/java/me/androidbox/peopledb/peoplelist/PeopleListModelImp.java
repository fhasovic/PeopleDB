package me.androidbox.peopledb.peoplelist;

import java.util.UUID;

import io.realm.Realm;
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

    /* Mock constructor */
    public PeopleListModelImp(int data) {
        /* Nothing to do here */
    }

    @Override
    public void deletePerson(final Person person, final DeleteListener deleteListener) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            String id = person.getId();
            @Override
            public void execute(Realm realm) {
                RealmResults<Person> results = realm.where(Person.class).equalTo("mId", id).findAll();
                if(results.deleteAllFromRealm()) {
                    Timber.d("deletePerson == true");
                }
                else {
                    Timber.e("deletePerson == false");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("onSuccess to delete person");
                deleteListener.onDeleteSuccess(person);
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
                realm.copyToRealm(person);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("insertPersonInto: onSuccess");
                insertIntoDBListener.onInsertSuccess(person);
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
                Timber.d("updatePerson onSuccess");
                updateDBListener.onUpdateSuccess(person);
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
        if(mRealm.isClosed()) {
            Timber.d("loadPersons mRealm is closed");
            mRealm = Realm.getDefaultInstance();
        }

        RealmResults<Person> personsList = mRealm.where(Person.class).findAll();
        if(personsList.size() > 0) {
            loadPersonListener.onLoadPersonSuccess(mRealm.copyFromRealm(personsList));
        }
        else {
            loadPersonListener.onLoadPersonFailure("No items in the database");
        }
    }

    @Override
    public void initializeResources() {
        if(mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
        }
    }

    /** Clean up everything to avoid memory leaks */
    @Override
    public void releaseResources() {
        Timber.d("releaseResources");
        if(!mRealm.isClosed()) {
            /* Cancel any pending transactions */
            if(mRealm.isInTransaction()) {
                mRealm.cancelTransaction();
            }
            mRealm.close();
            Timber.d("releaseResources closed");
        }
    }
}

