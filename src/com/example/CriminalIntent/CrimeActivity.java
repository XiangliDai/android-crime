package com.example.CriminalIntent;

import android.app.Activity;
import android.os.Bundle;
 import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
       // return new CrimeFragment();
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeListFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }


}
