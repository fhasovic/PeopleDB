package me.androidbox.peopledb.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.peopledb.peoplelist.PeopleListPresenterImp;

/**
 * Created by steve on 10/30/16.
 */
@Module
public class PeopleListPresenterModule {
    @Provides @Singleton
    public PeopleListPresenterImp providesPeopleListPresenter() {
        return new PeopleListPresenterImp();
    }
}
