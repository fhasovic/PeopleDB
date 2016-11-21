package me.androidbox.peopledb.peoplelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.transfuse.annotations.Bind;

import java.util.Collections;
import java.util.Iterator;
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
        notifyDataSetChanged();
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
            notifyDataSetChanged();
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

    /** Filter the adapter based on the query */
    public void filterQuery(String query) {
        Iterator<Person> iterator = mPersonList.iterator();

        while(iterator.hasNext()) {
            Person person = iterator.next();
            if(person.getFirstName().equalsIgnoreCase(query)) {
                clearAdapter();
                loadPerson(person);
                break;
            }
        }
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
        holder.mTvDob.setText(mPersonList.get(position).getDob());
        holder.mTvPhoneNumber.setText(mPersonList.get(position).getPhoneNumber());
        holder.mTvZipCode.setText(mPersonList.get(position).getZip());
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
        @BindView(R.id.tvDob) TextView mTvDob;
        @BindView(R.id.tvPhoneNumber) TextView mTvPhoneNumber;
        @BindView(R.id.tvZipCode) TextView mTvZipCode;

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
        //        mPersonSelectedListener.onDeletePersonSelected(getAdapterPosition());
            }
            return true;
        }
    }
}
