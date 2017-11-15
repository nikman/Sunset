package com.niku.andrew.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;


/**
 * Created by niku on 30.09.2017.
 */

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean mDay = true;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });

        return view;

    }

    private void startAnimation() {

        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        if (mDay) {

            ObjectAnimator heightAnimator = ObjectAnimator
                    .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                    .setDuration(4000);
            heightAnimator.setInterpolator(new AccelerateInterpolator());

            ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                    .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                    .setDuration(4000);
            sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

            ObjectAnimator nightSkyAnimator = ObjectAnimator
                    .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                    .setDuration(2000);
            nightSkyAnimator.setEvaluator(new ArgbEvaluator());

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet
                    .play(heightAnimator)
                    .with(sunsetSkyAnimator)
                    .before(nightSkyAnimator);

            animatorSet.start();

            mDay = false;

            Toast toast = Toast.makeText(getContext(), R.string.sleep, Toast.LENGTH_LONG);
            toast.show();

        } else {

            ObjectAnimator nightSkyAnimatorRise = ObjectAnimator
                    .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                    .setDuration(2000);
            nightSkyAnimatorRise.setEvaluator(new ArgbEvaluator());

            ObjectAnimator sunriseSkyAnimator = ObjectAnimator
                    .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                    .setDuration(4000);
            sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

            ObjectAnimator heightAnimatorRise = ObjectAnimator
                    .ofFloat(mSunView, "y", sunYEnd, sunYStart)
                    .setDuration(4000);
            heightAnimatorRise.setInterpolator(new AccelerateInterpolator());

            /*ObjectAnimator testAnimator = ObjectAnimator
                    .ofArgb(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                    .setDuration(2000);*/

            AnimatorSet animatorSet = new AnimatorSet();

            animatorSet
                    .play(heightAnimatorRise)
                    .with(nightSkyAnimatorRise)
                    .before(sunriseSkyAnimator);

            animatorSet.start();

            mDay = true;

            Toast toast = Toast.makeText(getContext(), R.string.wake, Toast.LENGTH_LONG);
            toast.show();

        }

    }

}
