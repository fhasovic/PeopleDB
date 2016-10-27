package me.androidbox.peopledb.peoplelist;

import java.util.UUID;

import io.realm.Realm;
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
    public void deletePerson(DeleteListener deleteListener) {

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
    public void updatePerson(UpdateDBListener updateDBListener) {

    }

    @Override
    public void releaseResources() {
        if(!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
