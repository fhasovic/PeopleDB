package me.androidbox.peopledb.peoplelist;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.di.DaggerInjector;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleView extends Fragment implements PeopleViewContract {

    @Inject PeopleListPresenterImp mPeopleListPresenter;
    @BindView(R.id.rvPeople) RecyclerView mRvPeople;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getAppComponent().inject(PeopleView.this);
        if(mPeopleListPresenter != null) {
            Timber.d("mPeopleListPresenter != null");
            mPeopleListPresenter.loadPersons();
        }
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void deletionFailure() {

    }

    @Override
    public void insertionSuccess() {

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
    public void loadSuccess() {

    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void getDob() {

    }

    @Override
    public void getFirstName() {

    }

    @Override
    public void getLastName() {

    }

    @Override
    public void getPhoneNumber() {

    }

    @Override
    public void getZip() {

    }
}
