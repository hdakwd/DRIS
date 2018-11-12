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
    net_method how_to;
    //ConnectivityManager cm;
    private TextView mText;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Drive-Recorder
                    mText.setText(how_to.net_status() );
                    return true;
                case R.id.navigation_dashboard:
                    mText.setText(how_to.net_status() );
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

        mText = findViewById(R.id.text);
        how_to = new net_method(getApplicationContext());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* ConnectivityManagerの取得 */
        //cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
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
}
