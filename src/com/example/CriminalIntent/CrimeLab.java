package com.example.CriminalIntent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by xdai on 11/5/13.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private static final String TAG ="CrimeLab";
    private static final String FILENAME = "crimes.json";
    private CriminalIntentJSONSerializer mSerializer;

    private CrimeLab(Context appContext)
    {
        this.mAppContext = appContext;

        mSerializer = new CriminalIntentJSONSerializer(mAppContext,FILENAME);
        try{
            mCrimes = mSerializer.loadCrimes();
        }
        catch (Exception e){
            mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes:", e);
        }

        /*for(int i=0;i<100;i++){
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i%2==0);
            mCrimes.add(c);
        }*/
    }

    public static CrimeLab get(Context c){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(c);

        return sCrimeLab;
    }
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    public ArrayList<Crime> getCrimes(){
     return mCrimes;
    }

    public boolean saveCrimes(){
        try{
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            return true;

        }
        catch (Exception ex){
            Log.d(TAG, "Error saving crimes to file");
            return false;

        }
    }
    public Crime getCrime(UUID id){
        for(Crime c: mCrimes){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

}
