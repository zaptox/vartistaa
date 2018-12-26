package com.vartista.www.vartista.util;

import android.app.Application;
import android.graphics.Color;
import android.support.v7.content.res.AppCompatResources;

import com.vartista.www.vartista.R;

import sakout.mehdi.StateViews.StateViewsBuilder;

/**
 * Created by Samina Arif on 11/26/2018.
 */

public class StateView extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StateViewsBuilder
                .init(this)
                .setIconColor(Color.parseColor("#D2D5DA"))
                .addState("error",
                        "No Connection",
                        "Error retrieving information from server.",
                        AppCompatResources.getDrawable(this, R.drawable.ic_server_error),
                        "Retry"
                )
                .addState("nodata",
                        "No Results Found",
                        "Unfortunately I could not find any results matching your search",
                        AppCompatResources.getDrawable(this, R.drawable.search), null);

    }
}

