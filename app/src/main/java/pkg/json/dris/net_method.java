package pkg.json.dris;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class net_method extends Activity {
    ConnectivityManager cm;
    NetworkInfo nInfo;

    WifiManager wifiManager;
    WifiInfo w_info;

    String status = "null";

    net_method() {
        nInfo = cm.getActiveNetworkInfo();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        w_info = wifiManager.getConnectionInfo();
    }

    public String net_status() {

        if (nInfo == null) {
            status = "disconnect";
        }

        if (nInfo.isConnected()) {
            /* NetWork接続可 */
            if (nInfo.getTypeName().equals("WIFI")) {
                if(w_info.getSSID() == "") status = "";
                else status = "Wi-Fi";

            } else if (nInfo.getTypeName().equals("mobile")) {
                status = "Mobile";
            }
        } else {
            /* NetWork接続不可 */
            status = "disconnect";
        }

        return status;
    }
}
