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

public class UpdatePersonDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface UpdatePersonListener {
        void onUpdatePerson(String firstName, String lastName, String dob, String phoneNumber, String zip);
    }

    public static final String PERSONUPDATE_KEY = "personupdate_key";

    private String mFirstname;

    @BindView(R.id.etFirstName) EditText mEtFirstName;
    @BindView(R.id.etLastName) EditText mEtLastName;
    @BindView(R.id.tvDob) TextView mTvDob;
    @BindView(R.id.etPhoneNumber) EditText mEtPhoneNumber;
    @BindView(R.id.etZipCode) EditText mEtZipCode;

    public UpdatePersonDialog() {
    }

    /*         String firstname,
            String lastname,
            String dob,
            String phoneNumber,
            String zipCode*/

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

        //String person;
     //   Bundle mbundle = getArguments();

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            if(bundle != null) {
                Person person = Parcels.unwrap(bundle.getParcelable(PERSONUPDATE_KEY));
                if (person != null) {
                    Timber.d("person: %s", person.getFirstName());
                }
            }
            else {
                Timber.e("Bundle == null");
            }
        }
        else {
            Timber.e("No bundle - make sure bundle attached");
        }

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
        UpdatePersonListener listener = (UpdatePersonDialog.UpdatePersonListener)getTargetFragment();
        listener.onUpdatePerson(
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
