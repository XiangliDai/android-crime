package com.example.CriminalIntent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xdai on 11/5/13.
 */
public class CrimeListFragment extends ListFragment {
    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;
    private static final String TAG = "CrimeListFragment";
    public static final String EXTRA_CRIME_ID="com.example.CriminalIntent.crime_id";
   // public ArrayAdapter(Context context, int textViewResourceId,T[] objects);
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);
       //getActivity().setTitle(R.string.crimes_title);
       setRetainInstance(true);
       mSubtitleVisible = false;
       mCrimes = CrimeLab.get(getActivity()).getCrimes();

       //ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),android.R.layout.simple_list_item_1,mCrimes);
       CrimeAdapter adapter = new CrimeAdapter(mCrimes);
       setListAdapter(adapter);
   }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = super.onCreateView(inflater,parent,savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(mSubtitleVisible)
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
        }
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        //registerForContextMenu(listView);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) registerForContextMenu(listView);
        else{
            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                }

                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    MenuInflater inflater1 = actionMode.getMenuInflater();
                    inflater1.inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }

                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for(int i=adapter.getCount()-1; i>= 0; i--){
                                if(getListView().isItemChecked(i)){
                                    crimeLab.deleteCrime(adapter.getItem(i));
                                }
                            }
                            actionMode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }

                }

                public void onDestroyActionMode(ActionMode actionMode) {

                }
            });
        }
        return  v;

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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                i.putExtra(EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if(getActivity().getActionBar().getSubtitle() == null){
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
                mSubtitleVisible =true;
                    item.setTitle(R.string.hide_subtitle);
                }
                else{
                    getActivity().getActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
        Crime crime = adapter.getItem(position);

        switch (item.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
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
