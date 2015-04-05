package com.example.grk_rise.ddc.activitys;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by GrK_Rise on 05.04.2015.
 */
public class FileContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.grk_rise.ddc/");
    private static final HashMap<String, String> MIME_TYPES = new HashMap<String, String>();
    static {
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    @Override
    public boolean onCreate() {
        try {
            File mFile = new File(getContext().getFilesDir(), "Image.jpg");
            if (!mFile.exists()) {
                mFile.createNewFile();
            }
            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            return (true);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public String getType(Uri uri) {
        String path = uri.toString();
        for(String extention : MIME_TYPES.keySet())
        {
            if(path.endsWith(extention))
            {
                return (MIME_TYPES.get(extention));
            }
        }
        return (null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException
    {
        File f = new File(getContext().getFilesDir(), "Image.jpg");
        if(f.exists()){
            return (ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_WRITE));
        }

        throw new FileNotFoundException(uri.getPath());
    }


}
