package me.androidbox.peopledb.peoplelist;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.androidbox.peopledb.R;

public class PeopleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_people_container, PeopleListView.getNewInstance(), "peopleview");
            fragmentTransaction.commit();
        }
    }
}
