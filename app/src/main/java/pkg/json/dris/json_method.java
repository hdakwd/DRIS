package pkg.json.dris;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class json_method {
    URL url;
    HttpURLConnection con;
    TextView textView;
    Context context;
    String html;

    public json_method() {
        //textView = findViewById(R.id.text);
    }

    public String accident_info() {
        Log.d("HTTP", "Clled accident_info() method.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    url = new URL("https://drivercd.tk/test.php");

                    con = (HttpURLConnection) url.openConnection();
                    final String str = InputStreamToString(con.getInputStream());
                    html = str;
                    Log.d("HTTP", str);
/*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(Json_Ana(String.valueOf(str)));
                        }
                    });
                    */
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
        return html;
    }

    //文字の組み立てメソッド
    private String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    //JSONに関するメソッド
    private String Json_Ana(String str) {
        String display = "";

        try {
            JSONObject json = new JSONObject(str);
            display = json.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return display;
    }
}