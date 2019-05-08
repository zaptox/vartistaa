package com.vartista.www.vartista.modules.general;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.choota.dev.ctimeago.TimeAgo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTimeAgo {

    SimpleDateFormat simpleDateFormat, dateFormat;
    DateFormat timeFormat;
    Date dateTimeNow;
    String timeFromData;
    String pastDate;
    String sDateTimeNow;

    @Nullable
    Context context;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEKS_MILLIS = 7 * DAY_MILLIS;
    private static final int MONTHS_MILLIS = 4 * WEEKS_MILLIS;
    //private static final int YEARS_MILLIS = 12 * MONTHS_MILLIS;

    public MyTimeAgo  locale(@NonNull Context context) {
        this.context = context;
        return this;
    }

    public MyTimeAgo() {

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timeFormat = new SimpleDateFormat("hh:mm ");


//        Calendar c = Calendar.getInstance();
//        if (c.get(Calendar.AM_PM)==0){
//            Date now = c.getTime();
//            sDateTimeNow = simpleDateFormat.format(now);
//
//        }else{
//            c.add(Calendar.HOUR,+12);
//            Date now = c.getTime();
//            sDateTimeNow = simpleDateFormat.format(now);
//        }

//        Date now = Calendar.getInstance().getTime();
        Date now = new Date();
        sDateTimeNow = simpleDateFormat.format(now);


//        Date now = new Date();

        try {
            dateTimeNow = simpleDateFormat.parse(sDateTimeNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTimeAgo(Date startDate) {

        //  date counting is done till todays date
        Date endDate = dateTimeNow;

        //  time difference in milli seconds
        long different = endDate.getTime() - startDate.getTime();

        if (context==null) {
            if (different < MINUTE_MILLIS) {
                return context.getResources().getString(com.choota.dev.ctimeago.R.string.just_now);
            } else if (different < 2 * MINUTE_MILLIS) {
                return context.getResources().getString(com.choota.dev.ctimeago.R.string.a_min_ago);
            } else if (different < 50 * MINUTE_MILLIS) {
                return different / MINUTE_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.mins_ago);
            } else if (different < 90 * MINUTE_MILLIS) {
                return context.getString(com.choota.dev.ctimeago.R.string.a_hour_ago);
            }
            else if (different < 24 * HOUR_MILLIS){
                return different/HOUR_MILLIS + "hours ago";
            }

//            else if (different < 24 * HOUR_MILLIS) {
//                timeFromData = timeFormat.format(startDate);
//                return timeFromData;
//            }
            else if (different < 48 * HOUR_MILLIS) {
                return context.getString(com.choota.dev.ctimeago.R.string.yesterday);
            } else if (different < 7 * DAY_MILLIS) {
                return different / DAY_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.days_ago);
            } else if (different < 2 * WEEKS_MILLIS) {
                return different / WEEKS_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.week_ago);
            } else if (different < 3.5 * WEEKS_MILLIS) {
                return different / WEEKS_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.weeks_ago);
            } else {
                pastDate = dateFormat.format(startDate);
                return pastDate;
            }
        } else {
            if (different < MINUTE_MILLIS) {
                return context.getResources().getString(com.choota.dev.ctimeago.R.string.just_now);
            } else if (different < 2 * MINUTE_MILLIS) {
                return context.getResources().getString(com.choota.dev.ctimeago.R.string.a_min_ago);
            } else if (different < 50 * MINUTE_MILLIS) {
                return different / MINUTE_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.mins_ago);
            } else if (different < 90 * MINUTE_MILLIS) {
                return context.getString(com.choota.dev.ctimeago.R.string.a_hour_ago);
            }
            else if (different < 24 * HOUR_MILLIS){
                return different/HOUR_MILLIS + " hours ago";
            }

//            else if (different < 24 * HOUR_MILLIS) {
//                timeFromData = timeFormat.format(startDate);
//                return timeFromData;
//            }
            else if (different < 48 * HOUR_MILLIS) {
                return context.getString(com.choota.dev.ctimeago.R.string.yesterday);
            } else if (different < 7 * DAY_MILLIS) {
                return different / DAY_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.days_ago);
            } else if (different < 2 * WEEKS_MILLIS) {
                return different / WEEKS_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.week_ago);
            } else if (different < 3.5 * WEEKS_MILLIS) {
                return different / WEEKS_MILLIS + context.getString(com.choota.dev.ctimeago.R.string.weeks_ago);
            } else {
                pastDate = dateFormat.format(startDate);
                return pastDate;
            }
        }
    }
}

