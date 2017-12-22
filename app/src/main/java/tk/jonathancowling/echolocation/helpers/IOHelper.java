package tk.jonathancowling.echolocation.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import tk.jonathancowling.echolocation.R;

/**
 * Created by jonathan on 18/11/17.
 */

public class IOHelper {

    private static final String SEPERATOR = "_";
    public static final String BASE_FILE_NAME = "cat";
    private static File pictureDir = null;
    private static String PICTURES_FILE_NAME = null;
    private static boolean writePermission;

    public static boolean getExternalStorageWritable(Context con){
        return PermissionHelper.hasPermission(con, PermissionHelper.Permission.PermissionName.WRITE)
                && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean createPictureDir(Context context){
        return getPictureDir(context).mkdir();
    }

    public static File getPictureDir(Context context) {
        if (pictureDir != null ) { // got picture dir, return it
            return pictureDir;
        }
        if (PICTURES_FILE_NAME == null){
            PICTURES_FILE_NAME = context.getResources().getString(R.string.app_name);
        }
        if (getExternalStorageWritable(context)){ // got picture availability, set the directory then return it
            pictureDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PICTURES_FILE_NAME);
            Log.d(IOHelper.class.getName(), "got permission for writing to picture directory: " + pictureDir);
            return pictureDir;
        }
        Log.d(IOHelper.class.getName(), "permission denied for writing to external storage");
        pictureDir = context.getFilesDir(); // no external file storage, use internal
        return pictureDir;
    }

    public static File getEmptyFile(File directory, String basename, String extension) {
        long count = 0L;

        File catFile = new File(directory, basename + "." + extension);
        while (catFile.exists()){
            catFile = new File(directory, basename + SEPERATOR + count + "." + extension);
            count++;
        }
        return catFile;
    }


    public static void save(File file, String contents) {
        FileWriter writer = null;
        try {
            writer =  new FileWriter(file);
            writer.write(contents);
        } catch (IOException ioe){
            Log.w(IOException.class.toString(), "FileWriter filed to write contents " + ioe);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ioe){
                Log.e(IOHelper.class.toString(), "FileWriter failed to close: " + ioe);
            }
        }
    }

    public static void save(File file, Byte[] contents) {
        FileOutputStream writer = null;
        try {
            writer =  new FileOutputStream(file);
            for (Byte c : contents) {
                writer.write(c);
            }
        } catch (IOException ioe){
            Log.w(IOException.class.toString(), "FileWriter filed to write contents " + ioe);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ioe){
                Log.e(IOHelper.class.toString(), "FileWriter failed to close: " + ioe);
            }
        }
    }
}
