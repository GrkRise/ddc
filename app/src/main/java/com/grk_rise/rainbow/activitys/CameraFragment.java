package com.grk_rise.rainbow.activitys;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.grk_rise.rainbow.R;
import com.grk_rise.rainbow.core.FileContentProvider;
import com.grk_rise.rainbow.core.KMeans;

import java.io.File;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends HomeActivity.PlaceholderFragment implements Button.OnClickListener{

    private static final int PHOTO_INTENT_REQUES_CODE = 100;
    private ImageView imageView;
    FloatingActionButton fabButton;
    Bitmap mBitmap;
    Bitmap rBitmap;
    KMeans kMeans;
    File out;
    ProgressBar progressBar;


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
        progressBar = (ProgressBar) rootView.findViewById(R.id.prb);


        TypedArray colors = getResources().obtainTypedArray(R.array.button);
        Random random = new Random();


        fabButton = new FloatingActionButton.Builder(this.getActivity())
                .withDrawble(getResources().getDrawable(R.drawable.ic_camera_shot))
                .withButtonColor(getResources().getColor(colors.getResourceId(random.nextInt(18), -1)))
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

            out = new File(getActivity().getFilesDir(), "Image.jpg");
            if(!out.exists()){
                Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                return;
            }

            mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            //mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() / 2,
                    mBitmap.getHeight() / 2, true));

            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    kMeans = new KMeans(mBitmap);
                    kMeans.k_means();
                    rBitmap = kMeans.getRslt();
                    return rBitmap;
                }

                @Override
                protected void onPostExecute(Object o) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    imageView.setImageBitmap(rBitmap);
                }
            };

            imageView.setVisibility(ImageView.VISIBLE);
            asyncTask.execute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
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
