package tk.jonathancowling.echolocation.adapters;

/**
 * Created by jonathan on 20/12/17.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.io.File;

import tk.jonathancowling.echolocation.fragments.ViewCatsFragment;
import tk.jonathancowling.echolocation.helpers.IOHelper;

/**
 * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewCatsPagerAdapter extends FragmentStatePagerAdapter {

    private Activity mActivity;
    private File mPictureDir;

    public ViewCatsPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        mActivity = activity;
        mPictureDir = IOHelper.getPictureDir(mActivity);
        if ( !mPictureDir.isDirectory()){
            Log.w(this.getClass().getName(), "picture directory is not a directory");
            IOHelper.createPictureDir(mActivity);
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return ViewCatsFragment.newInstance(mPictureDir, new File(mPictureDir.list()[position]));
    }

    @Override
    public int getCount() {
        // total pages.
        return mPictureDir.list().length;
    }
}
