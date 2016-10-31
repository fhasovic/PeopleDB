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
 * Created by steve on 10/31/16.
 */

public class AddPersonDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface AddPersonListener {
        void onAddPerson(String firstname);
    }

    @BindView(R.id.etFirstName) EditText mEtFirstName;
    @BindView(R.id.etLastName) EditText mEtLastName;
    @BindView(R.id.tvDob) TextView mTvDob;
    @BindView(R.id.etPhoneNumber) EditText mEtPhoneNumber;

    public AddPersonDialog() {

    }

    public static AddPersonDialog newInstance() {
        return new AddPersonDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_person, container);
        ButterKnife.bind(AddPersonDialog.this, view);

        return view;
    }

    @OnClick(R.id.tvDob)
    public void enterDate() {
        DialogFragment newFragment = new DatePicker();
        newFragment.setTargetFragment(AddPersonDialog.this, 0);
        newFragment.show(getFragmentManager(), "datepicker");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void sendBackResults() {
        AddPersonListener listener = (AddPersonListener)getTargetFragment();
        listener.onAddPerson("Steve Mason");
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
