package tk.jonathancowling.echolocation.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;

import tk.jonathancowling.echolocation.R;
import tk.jonathancowling.echolocation.activities.ViewCatsActivity;
import tk.jonathancowling.echolocation.helpers.ActivityHelper;
import tk.jonathancowling.echolocation.helpers.GetImageTask;
import tk.jonathancowling.echolocation.helpers.IOHelper;
import tk.jonathancowling.echolocation.utils.android.Volley.ByteRequest;

/**
 * Created by jonathan on 20/11/17.
 */

public class EnterCatDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private String selectedItem;

    @Override
    public Dialog onCreateDialog(final Bundle saveInstanceState){

        final Context context = getActivity();

        final View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_alert_enter_cat,
                null,
                false);

        Spinner s = view.findViewById(R.id.enter_cat_file_type);

        s.setAdapter(ArrayAdapter.createFromResource(context,
                R.array.image_file_type,
                android.R.layout.simple_spinner_dropdown_item));
        s.setOnItemSelectedListener(this);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        if (selectedItem == null){
                            return;
                        }
                        try {
                            URL url = new URL(((EditText) view.findViewById(R.id.editText)).getText().toString());
                            Volley.newRequestQueue(getActivity())
                                    .add(new ByteRequest(Request.Method.GET,
                                            url.toString(), new Response.Listener<Byte[]>() {
                                        @Override
                                        public void onResponse(Byte[] response) {
                                            final Byte[] res = response;
                                            new Thread(new Runnable(){
                                                @Override
                                                public void run() {
                                                    IOHelper.save(
                                                            IOHelper.getEmptyFile(
                                                                    IOHelper.getPictureDir(context.getApplicationContext()),
                                                                    IOHelper.BASE_FILE_NAME,
                                                                    selectedItem),
                                                            res);
                                                }
                                            }).start();

                                            Log.d(getTag(), "HTTP response = [data]");
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.w(getTag(), "Request failed, error: " + error);
                                        }
                                    }));
                            Toast.makeText(getActivity(), "got a cat, now go to the gallery",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } catch (MalformedURLException e){
                            Log.w(this.getClass().getName(), "url is invalid");
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {

                    }
                })
                .create();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItem = (String) adapterView.getItemAtPosition(i);
        Log.d(getTag(), "selected Item " + selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.w(getTag(), "nothing selected");
    }
}
