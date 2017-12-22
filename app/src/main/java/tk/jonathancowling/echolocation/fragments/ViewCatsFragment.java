package tk.jonathancowling.echolocation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import android.net.Uri;
import java.net.URISyntaxException;

import tk.jonathancowling.echolocation.R;
import tk.jonathancowling.echolocation.helpers.IOHelper;

/**
 * Created by jonathan on 20/12/17.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewCatsFragment extends android.support.v4.app.Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_DIRECTORY = "directory";
    private static final String ARG_FILE_NAME = "file_name";

    public ViewCatsFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewCatsFragment newInstance(File pictureDir, File imageFile) {
        ViewCatsFragment fragment = new ViewCatsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_DIRECTORY, pictureDir.getAbsolutePath());
        args.putString(ARG_FILE_NAME, imageFile.getName());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_cats, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.cat_image);

        imageView.setImageURI(new Uri.Builder()
            .path(getArguments().getString(ARG_DIRECTORY))
            .appendPath(getArguments().getString(ARG_FILE_NAME))
            .build());

        return rootView;
    }
}
