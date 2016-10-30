package me.androidbox.peopledb.di;

/**
 * Created by steve on 10/30/16.
 */

public class DaggerInjector {
    private static AppComponent sAppComponent =
            DaggerAppComponent.builder()
                    .peopleListModelModule(new PeopleListModelModule())
                    .peopleListPresenterModule(new PeopleListPresenterModule())
                    .build();

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
