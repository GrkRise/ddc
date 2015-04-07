package com.example.grk_rise.ddc.activitys;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grk_rise.ddc.R;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends HomeActivity.PlaceholderFragment implements Button.OnClickListener{

    private static final int PHOTO_INTENT_REQUES_CODE = 100;
    private ImageView imageView;

    String mCurrentPhotoPath;
    FloatingActionButton fabButton;


    public CameraFragment() {
        // Required empty public constructor

    }

    /**
     *
     * @param sectionNumber
     * @return
     */

    public static CameraFragment newInstace(int sectionNumber){
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_camera,
                container, false);


        imageView = (ImageView) rootView.findViewById(R.id.camshot);



        fabButton = new FloatingActionButton.Builder(this.getActivity())
                .withDrawble(getResources().getDrawable(R.drawable.ic_camera_shot))
                .withButtonColor(getResources().getColor(R.color.main))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();


        fabButton.setOnClickListener(this);



        return rootView;
    }

    protected void dispatchTakePictureIntent(){

        PackageManager pm = getActivity().getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, FileContentProvider.CONTENT_URI);
            startActivityForResult(i, PHOTO_INTENT_REQUES_CODE);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_INTENT_REQUES_CODE && resultCode == Activity.RESULT_OK){

            File out = new File(getActivity().getFilesDir(), "Image.jpg");
            if(!out.exists()){
                Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                return;
            }
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            imageView.setImageBitmap(mBitmap);

        }
    }

    @Override
    public void onClick(View v) {
        dispatchTakePictureIntent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fabButton.hideFAB();
        fabButton.setEnabled(false);
    }
}
