package me.androidbox.peopledb.peoplelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

import static java.util.Collections.addAll;

/**
 * Created by steve on 10/30/16.
 */

public class PeopleListAdapter extends  RecyclerView.Adapter<PeopleListAdapter.PeopleRealmViewHolder> {
    public interface PersonSelectedListener {
          void onPersonSelected(int position);
    }
    private PersonSelectedListener mPersonSelectedListner;

    public List<Person> mPersonList = Collections.emptyList();

    public PeopleListAdapter(Context context, List<Person> personList, PersonSelectedListener personSelectedListener) {
        mPersonSelectedListner = personSelectedListener;
        mPersonList = personList;
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    /** Load fresh data into the adpater */
    public void loadAllPersons(List<Person> persons) {
        Timber.d("loadPersonData %s", persons);
        mPersonList.addAll(persons);
        notifyItemRangeInserted(0, persons.size());
    }

    /** Get person from the list */
    public Person getPerson(int position) {
        return mPersonList.get(position);
    }

    /** Clear all elements from adapter */
    public void clearAdapter() {
        if(!mPersonList.isEmpty()) {
            Timber.d("clearAdapter %d to clear out", mPersonList.size());
            mPersonList.clear();
        }
    }
    /** Load person into the adapter */
    public void loadPerson(Person person) {
        mPersonList.add(person);
        notifyItemInserted(mPersonList.size() - 1);
    }

    /** Update an existing person */
    public void updatePerson(Person person) {
        final int index = mPersonList.indexOf(person);
        mPersonList.set(index, person);
        notifyItemChanged(index);
    }

    /** void deletePerson */
    public void removePerson(Person person) {
        final int index = mPersonList.indexOf(person);
        mPersonList.remove(person);
        notifyItemRemoved(index);
    }

    @Override
    public void onBindViewHolder(PeopleRealmViewHolder holder, int position) {
        holder.mTvFirstName.setText(mPersonList.get(position).getFirstName());
        holder.mTvLastName.setText(mPersonList.get(position).getLastName());
    }

    @Override
    public PeopleRealmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.people_row, parent, false);

        return new PeopleRealmViewHolder(view);
    }

    class PeopleRealmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
