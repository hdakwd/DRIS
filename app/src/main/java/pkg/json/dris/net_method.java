package pkg.json.dris;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class net_method {
    ConnectivityManager cm;
    NetworkInfo nInfo;
    Context context;

    public net_method(Context context) {
        this.context = context;
        //cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //nInfo = cm.getActiveNetworkInfo();
    }

    public String net_status() {

        cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        nInfo = cm.getActiveNetworkInfo();
        String status = "null";

        if (nInfo == null) {
            status = "disconnect";
        }else if (nInfo.isConnected()) {
            if (nInfo.getTypeName().equals("WIFI")) {
                status = "Wi-Fi";
            } else if (nInfo.getTypeName().equals("mobile")) {
                status = "connect";
            }else {
                status = "What?";
            }

        } else {
            status = "disconnect";
        }
        return status;
    }

}
