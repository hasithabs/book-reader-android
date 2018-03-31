package com.example.enters.book_reader.Helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by EnTeRs on 3/28/2018.
 */

public class ImageHelper {
    public static Bitmap getBitmapFromAsset(Context context, String imgName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open(imgName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
