package com.example.CriminalIntent;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdai on 11/5/13.
 */
public class CrimeListFragment extends ListFragment {
    private ArrayList<Crime> mCrimes;
    private static final String TAG = "CrimeListFragment";
    public static final String EXTRA_CRIME_ID="com.example.CriminalIntent.crime_id";
   // public ArrayAdapter(Context context, int textViewResourceId,T[] objects);
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);

       getActivity().setTitle(R.string.crimes_title);
       mCrimes = CrimeLab.get(getActivity()).getCrimes();

       //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),android.R.layout.simple_list_item_1,mCrimes);
       CrimeAdapter adapter = new CrimeAdapter(mCrimes);
       setListAdapter(adapter);
   }

    public void onListItemClick(ListView l, View v, int position, long id) {
      //  Crime c = (Crime)(getListAdapter()).getItem(position);

        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getId().toString() + " is clicked");
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }
    private class CrimeAdapter extends ArrayAdapter<Crime>{

        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(),0, crimes);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime c = getItem(position);
            TextView titleTextview = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextview);
            titleTextview.setText(c.getTitle());

            TextView dateTextview = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextview);
            dateTextview.setText(c.getDate().toString());

            CheckBox solvedCheckbox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckbox);
            solvedCheckbox.setChecked(c.isSolved());
            return convertView;
        }
    }
}
