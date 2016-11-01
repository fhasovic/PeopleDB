package me.androidbox.peopledb.peoplelist;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.peopledb.R;

/**
 * Created by steve on 11/1/16.
 */

public class UpdatePersonDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface AddPersonListener {
        void onAddPerson(String firstName, String lastName, String dob, String phoneNumber, String zip);
    }

    @BindView(R.id.etFirstName) EditText mEtFirstName;
    @BindView(R.id.etLastName) EditText mEtLastName;
    @BindView(R.id.tvDob) TextView mTvDob;
    @BindView(R.id.etPhoneNumber) EditText mEtPhoneNumber;
    @BindView(R.id.etZipCode) EditText mEtZipCode;

    public UpdatePersonDialog() {

    }

    public static UpdatePersonDialog newInstance() {
        return new UpdatePersonDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_person, container, false);
        ButterKnife.bind(UpdatePersonDialog.this, view);

        /* Cannot disable from touching outside */
        setCancelable(false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tvDob)
    public void enterDate() {
        DialogFragment newFragment = new DatePicker();
        newFragment.setTargetFragment(UpdatePersonDialog.this, 0);
        newFragment.show(getFragmentManager(), "datepicker");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void sendBackResults() {
        AddPersonDialog.AddPersonListener listener = (AddPersonDialog.AddPersonListener)getTargetFragment();
        listener.onAddPerson(
                mEtFirstName.getText().toString(),
                mEtLastName.getText().toString(),
                mEtPhoneNumber.getText().toString(),
                mTvDob.getText().toString(),
                mEtZipCode.getText().toString());

        dismiss();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCancel)
    public void cancel() {
        mEtFirstName.setText("");
        mEtLastName.setText("");
        mTvDob.setText("");
        mEtPhoneNumber.setText("");
        mEtZipCode.setText("");
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = format.format(calendar.getTime());
        mTvDob.setText(formattedDate);
    }
}
