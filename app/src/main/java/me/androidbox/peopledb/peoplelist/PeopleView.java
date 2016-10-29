package me.androidbox.peopledb.peoplelist;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.peopledb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleView extends Fragment {


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

        return view;
    }

}
