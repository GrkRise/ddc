package com.grk_rise.rainbow.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grk_rise.rainbow.R;

/**
 * Created by GrK_Rise on 03.02.2015.
 */
public class HomeFragment extends HomeActivity.PlaceholderFragment {


    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,
                container, false);
        //AdView adView = (AdView) rootView.findViewById(R.id.adView);



        return rootView;
    }


}
