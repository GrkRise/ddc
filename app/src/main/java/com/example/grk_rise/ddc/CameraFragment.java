package com.example.grk_rise.ddc;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends HomeActivity.PlaceholderFragment {

    static final int CAMERA_RESULT = 11111;
    private ImageView imageView;
    private ImageButton imageButton;

    public CameraFragment() {
        // Required empty public constructor
        super();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == CAMERA_RESULT)
        {
            Bitmap img = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(img);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_camera,
                container, false);


        return rootView;
    }




}
