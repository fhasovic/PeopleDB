package me.androidbox.peopledb.di;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


/**
 * Created by steve on 10/27/16.
 */

public class PeopleDBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        /* Initialize realm instead of passing the context */
        Realm.init(PeopleDBApplication.this);

        /* Configure realm database */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("PeopleDB.realm")
                .build();

        /* Set default configuration */
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
