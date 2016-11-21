package me.androidbox.peopledb.peoplelist;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.peopledb.R;
import me.androidbox.peopledb.model.Person;
import timber.log.Timber;

/**
 * Created by steve on 11/1/16.
 */

public class UpdatePersonDialog extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface UpdatePersonListener {
        void onUpdatePerson(Person person);
    }

    public static final String PERSONUPDATE_KEY = "personupdate_key";
    private Person mPerson;

    @BindView(R.id.etFirstName) EditText mEtFirstName;
    @BindView(R.id.etLastName) EditText mEtLastName;
    @BindView(R.id.tvDob) TextView mTvDob;
    @BindView(R.id.etPhoneNumber) EditText mEtPhoneNumber;
    @BindView(R.id.etZipCode) EditText mEtZipCode;

    public UpdatePersonDialog() {
    }

    public static UpdatePersonDialog newInstance(Bundle bundle) {
        UpdatePersonDialog updatePersonDialog = new UpdatePersonDialog();
        updatePersonDialog.setArguments(bundle);

        return updatePersonDialog;
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
        final View view = inflater.inflate(R.layout.update_person, container, false);
        ButterKnife.bind(UpdatePersonDialog.this, view);

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            mPerson = Parcels.unwrap(bundle.getParcelable(PERSONUPDATE_KEY));
            if (mPerson != null) {
                Timber.d("profile: %s", mPerson.getFirstName());
                populatePersonFields();
            }
        }
        else {
            Timber.e("No bundle - make sure bundle attached");
        }

        /* Cannot disable from touching outside */
        setCancelable(false);

        return view;
    }

    /** Populate the fields */
    private void populatePersonFields() {
        mEtFirstName.setText(mPerson.getFirstName());
        mEtLastName.setText(mPerson.getLastName());
        mTvDob.setText(mPerson.getDob());
        mEtPhoneNumber.setText(mPerson.getPhoneNumber());
        mEtZipCode.setText(mPerson.getZip());
    }

    /** Populate profile with updated fields */
    private void updatePerson() {
        mPerson.setFirstName(mEtFirstName.getText().toString());
        mPerson.setLastName(mEtLastName.getText().toString());
        mPerson.setDob(mTvDob.getText().toString());
        mPerson.setPhoneNumber(mEtPhoneNumber.getText().toString());
        mPerson.setZip(mEtZipCode.getText().toString());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.tvDob)
    public void enterDate() {
        DialogFragment newFragment = DatePicker.newInstance();
        newFragment.setTargetFragment(UpdatePersonDialog.this, 0);
        newFragment.show(getFragmentManager(), "datepicker");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void sendBackResults() {
        updatePerson();
        UpdatePersonListener listener = (UpdatePersonDialog.UpdatePersonListener)getTargetFragment();
        listener.onUpdatePerson(mPerson);

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

        dismiss();
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
