package me.androidbox.peopledb.peoplelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder> {
    public interface PersonSelectedListener {
  /*      void onPersonSelected(String firstname, String lastname, String dob, String phoneNumber, String zipCode);*/
        void onPersonSelected(int position);
    }
    private PersonSelectedListener mPersonSelectedListner;

    public List<Person> mPersonList = Collections.emptyList();

    public PeopleListAdapter(List<Person> personList, PersonSelectedListener personSelectedListener) {
        mPersonList = new ArrayList<>(personList);
        mPersonSelectedListner = personSelectedListener;
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.people_row, parent, false);

        return new PeopleViewHolder(view);
    }

    /** Load fresh data into the adpater */
    public void loadAllPersons(List<Person> persons) {
        Timber.d("loadPersonData %s", persons);
        mPersonList.addAll(persons);
        notifyDataSetChanged();
    }

    /** Load person into the adapter */
    public void loadPerson(Person person) {
        mPersonList.add(person);
        notifyItemInserted(mPersonList.size() - 1);
    }

    /* Get person from the list */
    public Person getPerson(int position) {
        return mPersonList.get(position);
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {
        holder.mTvFirstName.setText(mPersonList.get(position).getFirstName());
        holder.mTvLastName.setText(mPersonList.get(position).getLastName());
        holder.mIvThumbnail.setImageResource(R.mipmap.ic_launcher);
    }

    class PeopleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivThumbnail) ImageView mIvThumbnail;
        @BindView(R.id.tvFirstName) TextView mTvFirstName;
        @BindView(R.id.tvLastName) TextView mTvLastName;

        public PeopleViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(PeopleViewHolder.this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPersonSelectedListner.onPersonSelected(getAdapterPosition());
                }
            });
        }
    }
}
