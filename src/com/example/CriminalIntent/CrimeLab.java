package com.example.CriminalIntent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by xdai on 11/5/13.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;


    private CrimeLab(Context appContext)
    {
        this.mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
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

    public Crime getCrime(UUID id){
        for(Crime c: mCrimes){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

}
