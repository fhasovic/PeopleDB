package me.androidbox.peopledb.di;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by steve on 10/27/16.
 */

public class PeopleDBApplication extends Application {
 //   private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
  //      Fabric.with(this, new Crashlytics());

        Timber.plant(new Timber.DebugTree());

        /* Initialize and configure realm */
        warmUpRealmConfiguration();

        /* Setup dagger injection */
     //   mAppComponent = createAppComponent();
    }

    private void warmUpRealmConfiguration() {
        /* Initialize realm instead of passing the context */
        Realm.init(PeopleDBApplication.this);

        /* Configure realm database */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("PeopleDB.realm")
                .build();

        /* Set default configuration */
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .peopleListModelModule(new PeopleListModelModule())
                .peopleListPresenterModule(new PeopleListPresenterModule())
                .build();
    }

 /*   public AppComponent getAppComponent() {
        return mAppComponent;
    }*/
}
