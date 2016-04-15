package pe.servosa.android.util.internet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import pe.servosa.android.R;

/**
 * Created by ucweb02 on 13/04/2016.
 */
public class Connection {

    private static boolean isReachable;
    /**
     * Checking for all possible internet providers
     * **/
    public static boolean hasNetworkConnectivity(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.net.Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (android.net.Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    Log.d("Connection", "NETWORKNAME: " + networkInfo.getTypeName());
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            // test for connection
                            Log.d("Connection", "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        Log.d("Connection", "Not Has Connection");
        return false;
    }

    /**
     * Para un mejor rendimiento este proceso se lleva a cabo dentro de un AsyncTask
     * Revise la clase SendPingAccessInternetProcess.
     */
    @Deprecated
    public static void hasAccessToInternet(final Context mContext) {
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll()
//                    .build();
//            StrictMode.setThreadPolicy(policy);
//        }
//

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
                    int returnVal = process.waitFor();
                    isReachable = (returnVal == 0);
                    if (isReachable) {
                        Log.d("Connection", "Internet access");
                    } else {
                        Log.d("Connection", "No Internet access");
                        showNetworkSettingsActivity(mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void showMessageNotConnectedToNetwork(Context mContext) {
        Toast.makeText(mContext, mContext.getString(R.string.please_connect_to_internet), Toast.LENGTH_SHORT).show();
    }

    public static void showNetworkSettingsActivity(final Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.title_dialog_connect_to_internet))
                .setMessage(mContext.getString(R.string.message_dialog_connect_to_internet))
                .setPositiveButton("Red Movil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Wi-Fi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                        mContext.startActivity(gpsOptionsIntent);
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
