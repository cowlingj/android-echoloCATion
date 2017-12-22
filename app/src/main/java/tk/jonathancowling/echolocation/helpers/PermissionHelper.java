package tk.jonathancowling.echolocation.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jonathan on 19/12/17.
 */

public abstract class PermissionHelper {

    public static class Permission {

        public enum PermissionName {
            WRITE(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            public final String name;

            PermissionName(String requiredName) {
                name = requiredName;
            }
        }

        public abstract class PermissionCode {
            public static final int ALL = 0;
        }
    }

    public static boolean hasPermission(Context con, Permission.PermissionName p){
        return ContextCompat.checkSelfPermission(con, p.name) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean shouldShowExplanation(Activity activity, Permission.PermissionName... p){
        for (Permission.PermissionName perm : p){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, perm.name)){
                return true;
            }
        }
        return false;
    }

    public static final Permission.PermissionName[] REQUIRED_PERMISSIONS = { Permission.PermissionName.WRITE };

    public static void requestSimple(Activity activity, int code, Permission.PermissionName... p){
        List<String> permissionNames = new LinkedList<>();
        for (Permission.PermissionName perm : p) {
            if (!hasPermission(activity, perm)) {
                permissionNames.add(perm.name);
            }
        }
        if (permissionNames.size() == 0){
            return;
        }

        String[] names = new String[permissionNames.size()];
        permissionNames.toArray(names);
        ActivityCompat.requestPermissions(activity, names, code);
    }

    public static void requestComplete(Activity activity, int code, Permission.PermissionName... p){
        List<String> permissionNames = new LinkedList<>();
        boolean willShowExplaination = false;
        for (Permission.PermissionName perm : p) {
            if (!hasPermission(activity, perm)) {
                permissionNames.add(perm.name);
                willShowExplaination = willShowExplaination || shouldShowExplanation(activity, perm);
            }
        }
        if (permissionNames.size() == 0){
            return;
        }
        if (willShowExplaination) {
            showExplanation(activity);
        }
        String[] perms = new String[permissionNames.size()];
        permissionNames.toArray(perms);
        ActivityCompat.requestPermissions(activity, perms, code);
    }

    private static void showExplanation(Activity activity) {
       new AlertDialog.Builder(activity)
               .setTitle("Granting Permissions")
               .setMessage("Why we need permissions")
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(getClass().getName(), "shown explanation");
                    }
                }).create()
               .show();
    }


}
