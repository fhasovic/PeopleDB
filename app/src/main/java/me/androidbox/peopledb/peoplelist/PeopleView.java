package me.androidbox.peopledb.peoplelist;

import android.app.DatePickerDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.di.DaggerInjector;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleView extends Fragment implements
        PeopleViewContract,
        AddPersonDialog.AddPersonListener {

    @Inject PeopleListPresenterImp mPeopleListPresenter;
    @BindView(R.id.rvPeople) RecyclerView mRvPeople;

    private PeopleListAdapter mPeoplistAdapter;
    private Unbinder mUnbinder;
    private String mFirstName;
    private String mLastName;
    private String mDob;
    private String mPhoneNumber;
    private String mZip;

    public PeopleView() {
        // Required empty public constructor
    }

    public static PeopleView getNewInstance() {
        return new PeopleView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* presenter --> activity */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /* bundle --> presenter */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.people_view, container, false);

        mUnbinder = ButterKnife.bind(PeopleView.this, view);

        setupRecyclerView();

        return view;
    }

    /** Setup the recycler view and adapter */
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvPeople.setLayoutManager(linearLayoutManager);
        mPeoplistAdapter = new PeopleListAdapter(new ArrayList<Person>());
        mRvPeople.setAdapter(mPeoplistAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getAppComponent().inject(PeopleView.this);
        if(mPeopleListPresenter != null) {
            Timber.d("mPeopleListPresenter != null");
            mPeopleListPresenter.attachView(PeopleView.this);

            /* Load from database */
            mPeopleListPresenter.loadPersons();
        }

   //     onAddPerson("firstName", "seconname", "4848484", "3383747", "383737");
    }

    @Override
    public void onAddPerson(String firstName, String lastName, String dob, String phoneNumber, String zip) {
        Timber.d("onAddPerson %s", firstName);
        mFirstName = firstName;
        mLastName = lastName;
        mDob = dob;
        mPhoneNumber = phoneNumber;
        mZip = zip;

        mPeopleListPresenter.insertPerson();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabAddPerson)
    public void addNewPerson() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        AddPersonDialog addPersonDialog = AddPersonDialog.newInstance();
        addPersonDialog.setTargetFragment(PeopleView.this, 100);
        addPersonDialog.show(fragmentManager, "addpersondialog");
   }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPeopleListPresenter.detachView();
    }

    @Override
    public void deletionFailure() {

    }

    @Override
    public void insertionSuccess(Person person) {
        Timber.d("insertionSuccess");
        mPeoplistAdapter.loadPerson(person);
    }

    @Override
    public void insertionFailure() {

    }

    @Override
    public void deletionSuccess() {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void updateFailure() {

    }

    @Override
    public void loadSuccess(List<Person> personList) {
        Timber.d("loadSuccess: %d", personList.size());

        mPeoplistAdapter.loadAllPersons(personList);

        for(Person person : personList) {
            Timber.d("UUID: %s", person.getFirstName());
        }
    }

    @Override
    public void loadFailure() {

    }

    @Override
    public String getDob() {
        return mDob;
    }

    @Override
    public String getFirstName() {
        return mFirstName;
    }

    @Override
    public String getLastName() {
        return mLastName;
    }

    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public String getZip() {
        return mZip;
    }

}
