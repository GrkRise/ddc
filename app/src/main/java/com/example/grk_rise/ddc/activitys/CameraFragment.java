package com.example.grk_rise.ddc.activitys;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.grk_rise.ddc.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends HomeActivity.PlaceholderFragment implements Button.OnClickListener{

    private static final int PHOTO_INTENT_REQUES_CODE = 100;
    private ImageView imageView;

    String mCurrentPhotoPath;

    protected File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "DDC_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return  image;


    }

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

        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.button);
        imageView = (ImageView) rootView.findViewById(R.id.camshot);


        imageButton.setOnClickListener(this);



        return rootView;
    }

    protected void dispatchTakePictureIntent(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "DDC_" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(imageFileName, ".jpg", storageDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//
//
//        //if(photoFile != null)
//            {
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile((image)));
            startActivityForResult(takePictureIntent, PHOTO_INTENT_REQUES_CODE);
            //}


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PHOTO_INTENT_REQUES_CODE && resultCode == Activity.RESULT_OK){

            //SetFullImage();
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
            imageView.setMinimumHeight(1000);
            imageView.setMinimumWidth(1500);


        }
    }

    @Override
    public void onClick(View v) {
        dispatchTakePictureIntent();
    }



    private void SetFullImage(){
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();



        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        Log.e("", "Test");
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmpOptions);
        //BitmapFactory.decodeByteArray(data, 0, data.length, bmpOptions);
        int photoW = bmpOptions.outWidth;
        int photoH = bmpOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;
        bmpOptions.inPurgeable = true;

        Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath, bmpOptions);
        //Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, bmpOptions);
        imageView.setImageBitmap(bmp);

    }

}
