package me.androidbox.peopledb.peoplelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 10/30/16.
 */

public class PeopleListAdapter extends  RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder> {
    public interface PersonSelectedListener {
        void onUpdatePersonSelected(int position);
        void onDeletePersonSelected(int position);
    }
    private PersonSelectedListener mPersonSelectedListener;

    public List<Person> mPersonList = Collections.emptyList();

    public PeopleListAdapter(List<Person> personList, PersonSelectedListener personSelectedListener) {
        mPersonSelectedListener = personSelectedListener;
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

    /** Get profile from the list */
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
    /** Load profile into the adapter */
    public void loadPerson(Person person) {
        mPersonList.add(person);
        notifyItemInserted(mPersonList.size() - 1);
    }

    /** Update an existing profile */
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
    public void onBindViewHolder(PeopleViewHolder holder, int position) {
        holder.mTvFirstName.setText(mPersonList.get(position).getFirstName());
        holder.mTvLastName.setText(mPersonList.get(position).getLastName());
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.people_row, parent, false);

        return new PeopleViewHolder(view);
    }

    class PeopleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.ivThumbnail) ImageView mIvThumbnail;
        @BindView(R.id.tvFirstName) TextView mTvFirstName;
        @BindView(R.id.tvLastName) TextView mTvLastName;

        public PeopleViewHolder(View view) {
            super(view);

            ButterKnife.bind(PeopleViewHolder.this, view);
            view.setOnClickListener(PeopleViewHolder.this);
            /* Uses swipe to delete instead
            view.setOnLongClickListener(PeopleViewHolder.this); */
        }

        @Override
        public void onClick(View view) {
            if(mPersonSelectedListener != null) {
                mPersonSelectedListener.onUpdatePersonSelected(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(mPersonSelectedListener != null) {
                mPersonSelectedListener.onDeletePersonSelected(getAdapterPosition());
            }
            return true;
        }
    }
}
