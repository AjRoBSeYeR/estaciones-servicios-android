package android.training.demoapp.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.training.demoapp.R;
import android.util.Log;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = InternetReceiver.class.getSimpleName();

    private final InternetReceiverInterface listener;

    String toastMessage = "unknown intent action";

//    public InternetReceiver() {
//
//    }

    public InternetReceiver(InternetReceiverInterface listener) {
        this.listener = listener;
    }
//https://www.vogella.com/tutorials/AndroidBroadcastReceiver/article.html


    @Override
    public void onReceive(Context context, Intent intent){

        try{
            if (isOnline(context)) {
                toastMessage = "Internet disponible";
                Log.d("Network Available ", "YES");
                listener.getResponseInternetReceiver(true);
            } else {
                Log.d("Network Available ", "NO");
               toastMessage = context.getString(R.string.service_not_available);
                listener.getResponseInternetReceiver(false);
            }
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, "Error => "+e+" ");
        }
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public interface InternetReceiverInterface {
        void getResponseInternetReceiver(boolean response);
    }


}
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
////        boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
////                mobile != null && mobile.isConnectedOrConnecting();
//
//        boolean isConnected =isNetworkAvailable(context);
//        if (isConnected) {
//            Log.d("Network Available ", "YES");
//            toastMessage = "conectado";
//        } else {
//            Log.d("Network Available ", "NO");
//            toastMessage = "no conectado";
//        }
//        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
//
//    }
//
//    public boolean isNetworkAvailable(final Context context) {
//        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
//        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//    }
////    @Override
////    public void onReceive(Context context, Intent intent) {
////        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
////                WifiManager.WIFI_STATE_UNKNOWN);
////
////        switch(extraWifiState) {
////        case WifiManager.WIFI_STATE_DISABLED:
////            toastMessage = "WIFI_STATE_DISABLED";
////            break;
////            case WifiManager.WIFI_STATE_ENABLED:
////                toastMessage = "WIFI_STATE_ENABLED";
////                break;
////        case WifiManager.WIFI_STATE_ENABLING:
////            toastMessage = "WIFI_STATE_ENABLING";
////            break;
////        case WifiManager.WIFI_STATE_DISABLING:
////            toastMessage = "WIFI_STATE_DISABLING";
////            break;
////        case WifiManager.WIFI_STATE_UNKNOWN:
////            //do something with data if you desire so, I found it unreliable until now so i've done nothing with it
////        }
////        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
////    }
//
//
//}
