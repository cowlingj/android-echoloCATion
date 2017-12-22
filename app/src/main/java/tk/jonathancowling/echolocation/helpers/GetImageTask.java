package tk.jonathancowling.echolocation.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import tk.jonathancowling.echolocation.activities.ViewCatsActivity;
import tk.jonathancowling.echolocation.utils.optional.None;
import tk.jonathancowling.echolocation.utils.optional.Option;
import tk.jonathancowling.echolocation.utils.optional.Some;

/**
 * Created by jonathan on 16/12/17.
 */

public class GetImageTask extends AsyncTask<URL, Void, Option<List<String>>> {

    private WeakReference<Context> context;
    private RequestQueue queue;
    private Option<List<String>> data;

    @Deprecated
    public GetImageTask(Context con){
        context = new WeakReference<>(con);
        Log.d(getClass().getName(), con.toString());
        Volley.newRequestQueue(con);
    }

    @Override
    @Deprecated
    protected Option<List<String>> doInBackground(URL... urls) {
        data = new Some<List<String>>(new LinkedList<String>());
        for (URL url : urls){
            Log.i(getClass().getName(), url.toString());
            queue.add(new StringRequest(Request.Method.GET, url.toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            List<String> tmp = data.getOrElse(new LinkedList<String>());
                            tmp.add(response);
                            data = data.add(tmp); // wont affect a none
                        }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        data = new None<>();
                    }
                }));
        }
        return data;
    }

    @Override
    @Deprecated
    protected void onPostExecute(Option<List<String>> result) {
        if (result.err() || context == null){
            return;
        }

        List<FileWriter> writers = new LinkedList<>(); // for closing

        try {
            File directory = IOHelper.getPictureDir(context.get());
            Log.i(getClass().getName(), "File: " + directory + " is a directory: " + directory.isDirectory());

            for(String fileContents : result.get()) {
                File catFile = IOHelper.getEmptyFile(directory, IOHelper.BASE_FILE_NAME, "png");
                FileWriter out = new FileWriter(catFile);
                writers.add(out);

                out.write(fileContents);
            }

            ActivityHelper.loadActivity(context.get(), ViewCatsActivity.class);

        } catch (IOException ioe){
            Log.w(getClass().getName(), ioe.toString());
        } finally {
            for(FileWriter w : writers){
                try {
                    w.close();
                } catch (IOException ioe){
                    Log.e(getClass().getName(),ioe.toString());
                }
            }
        }
    }


}
