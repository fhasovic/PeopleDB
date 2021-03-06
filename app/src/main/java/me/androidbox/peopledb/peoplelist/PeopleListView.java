package me.androidbox.peopledb.peoplelist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

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
public class PeopleListView extends Fragment implements
        PeopleListViewContract,
        AddPersonDialog.AddPersonListener,
        UpdatePersonDialog.UpdatePersonListener,
        PeopleListAdapter.PersonSelectedListener {

    @Inject PeopleListPresenterImp mPeopleListPresenter;

    @BindView(R.id.rvPeople) RecyclerView mRvPeople;
    @BindView(R.id.tbApp) Toolbar mTbApp;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefresh;

    private PeopleListAdapter mPeoplistAdapter;
    private Unbinder mUnbinder;
    private String mFirstName;
    private String mLastName;
    private String mDob;
    private String mPhoneNumber;
    private String mZip;

    public PeopleListView() {
        // Required empty public constructor
    }

    public static PeopleListView getNewInstance() {
        return new PeopleListView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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

        mUnbinder = ButterKnife.bind(PeopleListView.this, view);

        setupToolbar();
        setupRecyclerView();
        setupSwipeToDismiss();
        setupSwipeRefresh();

        return view;
    }

    /** Refresh the data by reloading the data from the database */
    private void setupSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mPeoplistAdapter.getItemCount() > 0) {
                    mPeoplistAdapter.clearAdapter();
                    mPeopleListPresenter.loadPersons();
                }
                else {
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    /** Setup the toolbar */
    private void setupToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mTbApp);
    }

    /** Setup the recycler view and adapter */
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvPeople.setLayoutManager(linearLayoutManager);
        mPeoplistAdapter = new PeopleListAdapter(new ArrayList<Person>(), PeopleListView.this);
        mRvPeople.setAdapter(mPeoplistAdapter);
    }

    /** Setup swipe to dismiss */
    private void setupSwipeToDismiss() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRvPeople);
    }

    /** Swipe to dismiss items from the list */
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();
            Timber.d("onSwiped %d", index);
            final Person person = mPeoplistAdapter.getPerson(index);
            mPeopleListPresenter.deletePersons(person);
        }
    };

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getAppComponent().inject(PeopleListView.this);
        if(mPeopleListPresenter != null) {
            Timber.d("mPeopleListPresenter != null");
            mPeopleListPresenter.attachView(PeopleListView.this);

            /* Load from database */
            mPeopleListPresenter.loadPersons();
        }
    }

    @Override
    public void onUpdatePerson(final Person person) {
        Timber.d("onUpdatePerson %s", person.getFirstName());
        mPeopleListPresenter.updatePerson(person);
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

    @Override
    public void onUpdatePersonSelected(int position) {
        /* Get the profile from the list */
        final Person person = mPeoplistAdapter.getPerson(position);
        Timber.d("onPersonSelected: %s", person.getFirstName());

        Bundle bundle = new Bundle();
        bundle.putParcelable(UpdatePersonDialog.PERSONUPDATE_KEY, Parcels.wrap(person));
        FragmentManager fragmentManager = getFragmentManager();
        UpdatePersonDialog updatePersonDialog = UpdatePersonDialog.newInstance(bundle);
        updatePersonDialog.setTargetFragment(PeopleListView.this, 0);
        updatePersonDialog.show(fragmentManager, "updatepersondialog");
    }

    @Override
    public void onDeletePersonSelected(int position) {
        final Person person = mPeoplistAdapter.getPerson(position);
        mPeopleListPresenter.deletePersons(person);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabAddPerson)
    public void addNewPerson() {
        FragmentManager fragmentManager = getFragmentManager();
        AddPersonDialog addPersonDialog = AddPersonDialog.newInstance();
        addPersonDialog.setTargetFragment(PeopleListView.this, 100);
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
        Timber.d("deletionFailure");
    }

    @Override
    public void insertionSuccess(Person person) {
        Timber.d("insertionSuccess");
        mPeoplistAdapter.loadPerson(person);
    }

    @Override
    public void insertionFailure() {
        Timber.d("insertionFailure");
    }

    @Override
    public void deletionSuccess(Person person) {
        Timber.d("deletionSuccess");
        mPeoplistAdapter.removePerson(person);
    }

    @Override
    public void updateSuccess(Person person) {
        Timber.d("updateSuccess");
        /* Add to the adatper */
        mPeoplistAdapter.updatePerson(person);
    }

    @Override
    public void updateFailure() {
        Timber.d("updateFailure");
    }

    @Override
    public void loadSuccess(List<Person> personList) {
        Timber.d("loadSuccess: %d", personList.size());

        mPeoplistAdapter.clearAdapter();
        mPeoplistAdapter.loadAllPersons(personList);

        if(mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("onQueryTextSubmit: %s", query);

                mPeoplistAdapter.filterQuery(query);

                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void loadFailure() {
        Timber.e("loadFailure");
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
