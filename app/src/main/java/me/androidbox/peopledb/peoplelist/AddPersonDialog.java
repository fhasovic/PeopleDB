package me.androidbox.peopledb.peoplelist;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.peopledb.R;

/**
 * Created by steve on 10/31/16.
 */

public class AddPersonDialog extends DialogFragment {
    public interface AddPersonListener {
        void onAddPerson(String firstname);
    }

    @BindView(R.id.etFirstName) EditText mEtFirstName;
    @BindView(R.id.etLastName) EditText mEtLastName;
    @BindView(R.id.etDob) EditText mEtDob;
    @BindView(R.id.etPhoneNumber) EditText mEtPhoneNumber;

    public AddPersonDialog() {

    }

    public static AddPersonDialog newInstance() {
        return new AddPersonDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_person, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(AddPersonDialog.this, view);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void sendBackResults() {
        AddPersonListener listener = (AddPersonListener)getTargetFragment();
        listener.onAddPerson("Steve Mason");
        dismiss();
    }
}
