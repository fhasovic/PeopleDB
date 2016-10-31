package me.androidbox.peopledb.peoplelist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by steve on 10/31/16.
 */

public class DatePicker extends DialogFragment  {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog datePickerDialog;

    public DatePicker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();
        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

        return datePickerDialog;

        //  return new DatePickerDialog(getActivity(), DatePicker.this, year, month, day);
    }

    /*@Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.getDefault());
        String formattedDate = format.format(calendar.getTime());
        ((EditText)getActivity().findViewById(R.id.etDob)).setText(formattedDate);
    }*/
}
