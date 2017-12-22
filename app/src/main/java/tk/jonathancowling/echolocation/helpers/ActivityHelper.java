package tk.jonathancowling.echolocation.helpers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jonathan on 18/11/17.
 */

public class ActivityHelper {
    public static void loadActivity(Context context, Class clazz){
        Intent i = new Intent(context, clazz);
        context.startActivity(i);
    }
}
