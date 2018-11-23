package pkg.json.dris;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class json_method extends AsyncTask<String, Void, String> {
    private Activity m_Activity;
    private ProgressDialog m_ProgressDialog;
    //TextView txt;


    json_method(Activity activity) {
        // 呼び出し元のアクティビティ
        Log.d("HTTP","+++ TEST +++  "+activity);
        m_Activity = activity;
    }

    /* 実行前の事前処理 */
    @Override
    protected void onPreExecute() {
        // プログレスダイアログの生成
        this.m_ProgressDialog = new ProgressDialog(this.m_Activity);
        // プログレスダイアログの設定
        this.m_ProgressDialog.setMessage("取得中");  // メッセージをセット
        // プログレスダイアログの表示
        this.m_ProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... ImagePath) {
        //ここにバックグラウンドで行う処理を書く
        String rtn="";
        URL url;
        HttpURLConnection con;

        try {
            url = new URL("https://drivercd.tk/test.php");

            con = (HttpURLConnection) url.openConnection();
            rtn = InputStreamToString(con.getInputStream());
            //rtn = str;
            Log.d("HTTP", rtn);
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return rtn;
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

    /* ここにdoInBackground終了後に行う処理を書く
       UI操作の記述ができる。 */
    @Override
    protected void onPostExecute(String result) {
        // txt = m_Activity.findViewById(R.id.result);
        // txt.setText(result);

        // プログレスダイアログを閉じる
        if (this.m_ProgressDialog != null && this.m_ProgressDialog.isShowing()) {
            this.m_ProgressDialog.dismiss();
        }
    }

    /* キャンセル時の処理 */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        //Log.v("AsyncTaskProgressDialogSimpleThread", "onCancelled()");
        if (this.m_ProgressDialog != null) {
            //Log.v("this.m_ProgressDialog.isShowing()", String.valueOf(this.m_ProgressDialog.isShowing()));
            // プログレスダイアログ表示中の場合
            if (this.m_ProgressDialog.isShowing()) {
                // プログレスダイアログを閉じる
                this.m_ProgressDialog.dismiss();
            }
        }
    }
}