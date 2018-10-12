package com.vartista.www.vartista.modules.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.vartista.www.vartista.R;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

public class AssignRatings extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_ratings);



        ScaleRatingBar ratingBar = findViewById(R.id.simpleRatingBar);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setRating(3);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);

        ratingBar.setIsIndicator(false);
        ratingBar.setClickable(true);
        ratingBar.setScrollable(true);
        ratingBar.setClearRatingEnabled(true);
        ratingBar.setEmptyDrawableRes(R.drawable.empty);
        ratingBar.setFilledDrawableRes(R.drawable.filled);
        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {

                Toast.makeText(AssignRatings.this, "ye hain rating"+v, Toast.LENGTH_SHORT).show();
            }


        });

    }
}
