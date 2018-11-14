package pkg.json.dris;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

public class net_method {
    ConnectivityManager cm;
    NetworkInfo nInfo;
    Context context;
    String[] net_state_kind = {"drireco", "internet", "disconnect", "Error"};

    public net_method(Context context) {
        this.context = context;
        //cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //nInfo = cm.getActiveNetworkInfo();
    }

    public String net_status() {
        cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        nInfo = cm.getActiveNetworkInfo();

        WifiManager manager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String status = "null";

        if (nInfo == null) {
            status = "Disconnect: Null";
        }else if (nInfo.isConnected()) {
            if (nInfo.getTypeName().equals("WIFI")) {
                if(info.getSSID().equals("drireco")) {
                    // "drireco"のアクセスポイントに接続中
                    status = net_state_kind[0];
                }else {
                    // "Wi-Fi"でインターネット接続中
                    status = net_state_kind[1];
                }
            } else if (nInfo.getTypeName().equals("mobile")) {
                // "Mobile"でインターネット接続
                status = net_state_kind[1];
            }else {
                //もしもの為の、謎のエラー。
                status = net_state_kind[3] +": Wi-Fi in if";
            }
        } else {
            //nInfo.isConnected()で、falseを返された。
            status = net_state_kind[2] +": False";
        }
        return status;
    }

}
