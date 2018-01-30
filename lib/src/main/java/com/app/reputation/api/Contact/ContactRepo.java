package com.app.reputation.api.Contact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import github.ankushsachdeva.emojicon.R;

/**
 * Created by michael on 23/01/18.
 */

public class ContactRepo {

    private ContactRepo() {

    }

    private boolean samplesWritten = false;
    private static ContactRepo instance;

    public static ContactRepo Get() {
        if(instance == null) {
            instance = new ContactRepo();
        }
        return  instance;
    }

    public void writeSampleImagesToFile(Context context) {

        if(!samplesWritten) {

            setContactImageDirectory(context);

            Field[] drawables = github.ankushsachdeva.emojicon.R.drawable.class.getFields();
            for (Field f : drawables) {
                try {
                    String fileName = f.getName();


                    if (fileName.contains("contactimage")) {

                        File outFile = context.getDir("Images", Context.MODE_PRIVATE);

                        int id = f.getInt(null);

                        Drawable drawable = context.getResources().getDrawable(id);

                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        outFile = new File(outFile, f.getName() + ".png");

                        OutputStream stream = null;

                        stream = new FileOutputStream(outFile);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                        stream.flush();

                        stream.close();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            samplesWritten = true;
        }

    }

    public static void setContactImageDirectory(Context context) {
        File dir = context.getDir("Images", Context.MODE_PRIVATE); //Creating an internal dir;
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        else {
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }
        }
    }

    //File filter
    // File representing the folder that you select using a FileChooser
    static final File dir = new File("Images");


    static final String[] CONTACTIMAGES = new String[]{
            "contactimage1", "contactimage2", "contactimage3", "contactimage4", "contactimage5", "contactimage6", "contactimage7", "contactimage8", "contactimage9", "contactimage10", "contactimage11" // and other formats you need
    };
    // filter to identify images based on their extensions
    public static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ct : CONTACTIMAGES) {
                if (name.contains(ct)) {
                    return (true);
                }
            }
            return (false);
        }
    };

}
