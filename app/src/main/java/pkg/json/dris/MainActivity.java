package pkg.json.dris;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.Observer {
    net_method how_to;
    json_method json;
    ConnectionReceiver mReceiver;
    TextView text;
    String connection;
    private TextView mText;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            TableLayout layout = findViewById(R.id.main_view);
            layout.removeAllViews();

            switch (item.getItemId()) {
                    /* 1. 画面を開く
                       2. 回線をチェックする
                       3. 開いているタブによって操作不能にする。
                     */
                case R.id.drireco:
                    //Drive-Recorder
                    mText.setText(how_to.net_status() );
                    getLayoutInflater().inflate(R.layout.recorder_view, layout);
                    if(connection.equals("drireco")) {
                        //ドライブレコーダに接続できているなら、タッチ操作で動画のダウンロード
                    }else {
                        //接続できていなきゃ、タッチ操作は封じる
                        mText.setText("現在ドライブレコーダには接続できていません。" + how_to.net_status() + json.accident_info());
                    }
                    return true;
                case R.id.internet:
                    //Mobile and other Wi-Fi
                    getLayoutInflater().inflate(R.layout.internet_view, layout);
                    if(connection.equals("internet")) {
                        //実際にネット接続できているなら、タッチ操作で詳細表示
                        mText.setText(json.accident_info());
                        //setContentView(R.layout.internet_view);
                    }else {
                        //実際にはネット接続できていないなら、タッチ操作は封じる
                        mText.setText("現在インターネットには接続できていません。" + how_to.net_status() + json.accident_info());
                    }
                    return true;
                case R.id.setting:
                    //"Setting" menu
                    mText.setText("Setting!!!");
                    getLayoutInflater().inflate(R.layout.setting_view, layout);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = findViewById(R.id.text);
        how_to = new net_method(getApplicationContext());
        json = new json_method();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // オプションメニューを作成する
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Wi-FiまたはMobile接続時のときに更新処理を始める。
        開いているメニューと実際の接続のプロトコル確認、
        マッチしていればメニュー通りの更新をする。
         */
        String str= "";

        // オプションメニュー
        if (item.getItemId() == R.id.item) {
            //更新処理記述場所
            str="更新処理";
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("更新")
                .setMessage(str +"を記述します")
                .setPositiveButton("OK", null)
                .show();

        return super.onOptionsItemSelected(item);
    }

    /* ConnectionReciver によって必要なるメソッド。*/
    @Override
    public void onResume() {
        super.onResume();
        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new ConnectionReceiver(this);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void driveConnect() {
        //Connected with Wi-Fi access point "drireco"
        /* ドライブレコーダに接続中の為、
        インターネット接続時のタブを開いていれば、タブを封じる */
        connection = "drireco";
        mText.setText("Drive-Recorder!");
    }

    @Override
    public void onConnect() {
        //ネットワークに接続した時の処理
        /* ドライブレコーダのタブを封じる */
        connection = "internet";
        mText.setText("Connect!");
    }

    @Override
    public void onDisconnect() {
        //ネットワークが切断された時の処理
        /* 両方のタブを封じる */
        connection = "disconnection";
        mText.setText("disconnect");
    }
    /* 上記、ConnectionReciver により必要な３点 */
}
