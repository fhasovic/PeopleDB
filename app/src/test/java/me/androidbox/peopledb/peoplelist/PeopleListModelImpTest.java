package me.androidbox.peopledb.peoplelist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import me.androidbox.peopledb.model.Person;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by steve on 10/29/16.
 */
public class PeopleListModelImpTest {

    private PeopleListModelContract.DeleteListener mMockDeleteListener;
    private PeopleListModelContract.InsertIntoDBListener mMockInsertListener;
    private PeopleListModelContract mPeopleListModel;
    private Person mPerson;

    @Before
    public void setUp() throws Exception {
        mMockDeleteListener = Mockito.mock(PeopleListModelContract.DeleteListener.class);
        mMockInsertListener = Mockito.mock(PeopleListModelContract.InsertIntoDBListener.class);
        mPeopleListModel = new PeopleListModelImp(anyInt());

        mPerson = new Person();
        mPerson.setPhoneNumber("929-280-9293");
        mPerson.setFirstName("steve");
        mPerson.setLastName("blogs");
        mPerson.setId(UUID.randomUUID().toString());
        mPerson.setDob("29-Oct-2016");
    }

    @Test
    public void shouldSuccessInsertingNewPerson() {
        mPeopleListModel.insertPersonInto(mPerson, mMockInsertListener);

        /* Mock a call for an success */
        verify(mMockInsertListener, times(1)).onInsertSuccess(mPerson);

        /* Verify that the calls were called */
        verify(mMockInsertListener, times(1)).onInsertSuccess(mPerson);
        verify(mMockInsertListener, never()).onInsertFailure(anyString());
    }

    @Test
    public void shouldFailureInsertingNewPerson() {
        mPeopleListModel.insertPersonInto(mPerson, mMockInsertListener);

        /* Mock a call for an failure */
        verify(mMockInsertListener, times(1)).onInsertSuccess(mPerson);

        /* Verify the the calls were called */
        verify(mMockInsertListener, times(1)).onInsertFailure(anyString());
        verify(mMockInsertListener, times(0)).onInsertSuccess(mPerson);
    }

    @After
    public void tearDown() throws Exception {

    }



}