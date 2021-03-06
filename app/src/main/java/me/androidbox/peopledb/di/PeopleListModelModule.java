package me.androidbox.peopledb.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.peopledb.peoplelist.PeopleListModelImp;

/**
 * Created by steve on 10/30/16.
 */
@Module
public class PeopleListModelModule {
    @Provides @Singleton
    public PeopleListModelImp providesPeopleListModelImp() {
        return new PeopleListModelImp();
    }
}
