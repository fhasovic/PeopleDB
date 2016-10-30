package me.androidbox.peopledb.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.peopledb.peoplelist.PeopleListPresenterImp;
import me.androidbox.peopledb.peoplelist.PeopleView;

/**
 * Created by steve on 10/30/16.
 */

@Singleton
@Component(modules = {PeopleListModelModule.class, PeopleListPresenterModule.class})
public interface AppComponent {
    void inject(PeopleListPresenterImp target);
    void inject(PeopleView target);
}
