package pkg.json.dris;

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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager cm;
    ImageView iv;
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //画面下のメニュー画面で選択されるたびにネット接続確認
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Drive-Recorder
                    return true;
                case R.id.navigation_dashboard:
                    //Mobile and other Wi-Fi
                    return true;
                case R.id.navigation_notifications:
                    //"Setting" menu
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* ConnectivityManagerの取得 */
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // オプションメニューを作成する
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Wi-Fiまたはmobile接続の時に更新処理を始める
        String str= "";

        // オプションメニュー
        if (item.getItemId() == R.id.item) {
            str="更新処理";
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("更新")
                .setMessage(str +"を記述します")
                .setPositiveButton("OK", null)
                .show();

        return super.onOptionsItemSelected(item);
    }
}
