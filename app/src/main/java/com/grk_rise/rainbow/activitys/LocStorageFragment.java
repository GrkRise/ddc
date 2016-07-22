package com.grk_rise.rainbow.activitys;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grk_rise.rainbow.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class LocStorageFragment extends HomeActivity.PlaceholderFragment {


    public LocStorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loc_storage, container, false);
    }


}
