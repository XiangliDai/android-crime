package com.example.CriminalIntent;

import android.support.v4.app.Fragment;

/**
 * Created by xdai on 11/5/13.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
