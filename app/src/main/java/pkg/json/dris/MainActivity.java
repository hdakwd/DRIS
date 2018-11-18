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
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.Observer {
    net_method how_to;
    json_method json; // JSONの受信において使うメソッド
    ConnectionReceiver mReceiver; //　ネットワーク接続を適宜知らせてくれる
    LinearLayout tab_view; //タブの切り替えに使う
    String connection; //接続先がドラレコか一別する
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /* LinearLayoutでタブの切り替えを行うため、
               activity_mainのリセットが必要 */
            tab_view.removeAllViews();

            /* 各タブを作成するinflate() */
            switch (item.getItemId()) {
                case R.id.drireco:
                    //Drive-Recorder
                    /* 各タブのリスト作成を作成するinflate() */
                    setTab_view(R.layout.recorder_view);

                    if(connection.equals("drireco")) {
                        //タッチ操作で動画のダウンロード等の操作を可能に
                        Log.d("HTTP", "drireco of Wi-Fi access point.");
                    }else {
                        //タッチ操作を封じる
                        Log.d("HTTP", "connected or disconnected internet.");
                    }
                    return true;
                case R.id.internet:
                    //Mobile and Other Wi-Fi
                    setTab_view(R.layout.internet_view);
                    if(connection.equals("internet")) {
                        //タッチ操作で詳細表示もしくはIntentでブラウザ展開
                        Log.d("HTTP", "connected internet.");
                        Log.d("HTTP", ""+json.accident_info());
                    }else {
                        //タッチ操作を封じる
                        Log.d("HTTP", "disconnected internet");
                    }
                    return true;
                case R.id.setting:
                    //"Setting" menu
                    setTab_view(R.layout.setting_view);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab_view = findViewById(R.id.tab_content);
        how_to = new net_method(getApplicationContext());
        json = new json_method();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationの後に呼び出さないと、起動時にタブの内容が表示されない。
        setTab_view(R.layout.recorder_view);
    }


    public void setTab_view(int layout) {
        //"ドラレコ"タブ、"インターネット"タブ、"設定"タブで、条件分岐または呼び出し元の変更が必要。
        int cnt = 0;
        String str = "";
        if(layout == R.layout.recorder_view) { str = "ドラレコ"; cnt = 10; }
        else if(layout == R.layout.internet_view) { str = "インターネット"; cnt = 20;}
        else if(layout == R.layout.setting_view) { str = "設定"; }

        LinearLayout cardLinear = this.findViewById(R.id.tab_content);
        cardLinear.removeAllViews();

        for(int i = 0; i< cnt; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(layout, null);
            CardView cardView = linearLayout.findViewById(R.id.cardView);
            TextView textBox = linearLayout.findViewById(R.id.textBox);
            textBox.setText(str + i);
            cardView.setTag(i);
            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, String.valueOf(v.getTag()) + "番目のCardViewがクリックされました", Toast.LENGTH_SHORT).show();
                }
            });
            cardLinear.addView(linearLayout,i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // オプションメニューを作成する
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // オプションメニュー
        /*　Wi-FiまたはMobile接続時のときに更新処理を始める。
            開いているメニューと実際の接続のプロトコル確認、
            マッチしていればメニュー通りの更新をする。 */
        String str= "";

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
    }

    @Override
    public void onConnect() {
        //ネットワークに接続した時の処理
        /* ドライブレコーダのタブを封じる */
        connection = "internet";
    }

    @Override
    public void onDisconnect() {
        //ネットワークが切断された時の処理
        /* 両方のタブを封じる */
        connection = "disconnection";
    }
    /* 上記、ConnectionReciver により必要な３点 */
}
