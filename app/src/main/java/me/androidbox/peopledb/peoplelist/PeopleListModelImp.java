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
    public void deletePerson(Person person, final DeleteListener deleteListener) {
        
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
    public void updatePerson(Person person, UpdateDBListener updateDBListener) {

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
            mRealm.close();
        }
    }
}
