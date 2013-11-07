package com.example.CriminalIntent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonParser;

/**
 * Created by xdai on 11/6/13.
 */
public class CriminalIntentJSONSerializerWithJackson {
    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializerWithJackson(Context c, String f){
        mContext = c;
        mFilename =f;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for(Crime c: crimes)

                array.put(c.toJSON());

        Writer writer = null;
        try{
            OutputStream out = null;

            out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);

            writer = new OutputStreamWriter(out);
            writer.write(array.toString());

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(writer !=null)
                writer.close();
        }
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException{
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try{

            InputStream input = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder jsonStr = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) !=null) jsonStr.append(line);
            JSONArray array =  (JSONArray)new JSONTokener(jsonStr.toString()).nextValue();
            for(int i=0; i< array.length(); i++)
                crimes.add(new Crime(array.getJSONObject(i)));
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        finally {
            if(reader != null)
                reader.close();
        }
        return crimes;
    }
}
