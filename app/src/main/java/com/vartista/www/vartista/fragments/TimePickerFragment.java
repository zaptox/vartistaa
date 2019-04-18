package com.vartista.www.vartista.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;


public class      TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this,hour,minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(mCallback!=null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(hourOfDay);
            sb.append(":");
            sb.append(minute);
            mCallback.returnTime(sb.toString());
        }

    }

    public interface PickTime
    {
        public void returnTime(String value);

    }

    PickTime mCallback;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mCallback = (PickTime) activity;
    }
}
