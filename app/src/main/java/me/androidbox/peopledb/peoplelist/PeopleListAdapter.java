package me.androidbox.peopledb.peoplelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 10/30/16.
 */

public class PeopleListAdapter extends RealmBasedRecyclerViewAdapter<Person, PeopleListAdapter.PeopleRealmViewHolder> {
    public interface PersonSelectedListener {
          void onPersonSelected(int position);
    }
    private PersonSelectedListener mPersonSelectedListner;

//    public List<Person> mPersonList = Collections.emptyList();

    public PeopleListAdapter(Context context, RealmResults<Person> realmResults, PersonSelectedListener personSelectedListener) {
        super(context, realmResults, false, true);
        mPersonSelectedListner = personSelectedListener;
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }


    /** Load fresh data into the adpater */
    public void loadAllPersons(List<Person> persons) {
        Timber.d("loadPersonData %s", persons);
  //      realmResults.addAll(persons);
        notifyDataSetChanged();
    }

    /** Get person from the list */
    public Person getPerson(int position) {
/*
        final Person person = mPersonList.get(position);
        return person;
*/
        return null;
    }

    /** Clear all elements from adapter */
    public void clearAdapter() {
 /*       if(!mPersonList.isEmpty()) {
            Timber.d("clearAdapter %d to clear out", mPersonList.size());
            mPersonList.clear();
        }*/
    }
    /** Load person into the adapter */
    public void loadPerson(Person person) {
/*
        mPersonList.add(person);
        notifyItemInserted(mPersonList.size() - 1);
*/
    }

    /** Update an existing person */
    public void updatePerson(Person person) {
/*
        final int index = mPersonList.indexOf(person);
        mPersonList.set(index, person);
        notifyItemChanged(index);
*/
    }

    /** void deletePerson */
    public void removePerson(Person person) {
/*
        final int index = mPersonList.indexOf(person);
        mPersonList.remove(person);
        notifyItemRemoved(index);
*/
    }

    @Override
    public void onBindRealmViewHolder(PeopleRealmViewHolder holder, int position) {
        holder.mTvFirstName.setText(realmResults.get(position).getFirstName());
        holder.mTvLastName.setText(realmResults.get(position).getLastName());
        holder.mIvThumbnail.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public PeopleRealmViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(R.layout.people_row, viewGroup, false);

        return new PeopleRealmViewHolder(view);
    }

    class PeopleRealmViewHolder extends RealmViewHolder implements View.OnClickListener{
        @BindView(R.id.ivThumbnail) ImageView mIvThumbnail;
        @BindView(R.id.tvFirstName) TextView mTvFirstName;
        @BindView(R.id.tvLastName) TextView mTvLastName;

        public PeopleRealmViewHolder(View view) {
            super(view);

            ButterKnife.bind(PeopleRealmViewHolder.this, view);
            view.setOnClickListener(PeopleRealmViewHolder.this);
        }

        @Override
        public void onClick(View view) {
            mPersonSelectedListner.onPersonSelected(getAdapterPosition());
        }
    }
}
