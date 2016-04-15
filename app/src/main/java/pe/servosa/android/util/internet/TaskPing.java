package pe.servosa.android.util.internet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ucweb02 on 13/04/2016.
 */
public class TaskPing extends AsyncTask<Object, Integer, ArrayList> {

    @Override
    protected ArrayList doInBackground(Object... params) {
        Boolean isReachable = false;
        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = process.waitFor();
            isReachable = (returnVal == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(isReachable, params[0]));
    }

    @Override
    protected void onPostExecute(ArrayList data) {
        if ( (Boolean) data.get(0) ) {
            Log.d("Connection", "Internet access");
        } else {
            Log.d("Connection", "No Internet access");
            Connection.showNetworkSettingsActivity((Context) data.get(1));
        }
    }
}