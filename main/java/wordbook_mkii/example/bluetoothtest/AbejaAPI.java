package wordbook_mkii.example.bluetoothtest;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class AbejaAPI extends AsyncTask<String, Void, String> {
    private static final
    String API_URL_PREFIX =
            "https://dcon22-akashi-nct-intel.api.abeja.io/deployments/2626261708867/services/ser-ae1e7457794f4ecb";
    private final WeakReference<TextView> titleViewReference;
    private final WeakReference<TextView> dateViewReference;


    public AbejaAPI(WeakReference<TextView> titleViewReference, WeakReference<TextView> dateViewReference) {
        this.titleViewReference = titleViewReference;
        this.dateViewReference = dateViewReference;
    }

    @Override
    protected String doInBackground(String... params) {
        final StringBuilder result = new StringBuilder();
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority(API_URL_PREFIX);
        uriBuilder.path("/books/v1/volumes");
        uriBuilder.appendQueryParameter("q", "夏目漱石");
        final String uriStr = uriBuilder.build().toString();
        String urlll = "-X POST      -u user-2576859132064:3ffb4e8ec387aa567414152d5b02a12b767924fd      -H \"Content-Type: image/png\"      --data-binary @midtest_1.png      https://dcon22-akashi-nct-intel.api.abeja.io/deployments/2626261708867/services/ser-ae1e7457794f4ecb\n";
        try {
            URL url = new URL(urlll);
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
          //  con.setRequestMethod("POST");
          //  con.addRequestProperty("");
            con.setDoInput(true);
            con.connect(); //HTTP接続

            final InputStream in = con.getInputStream();
            final InputStreamReader inReader = new InputStreamReader(in);
            final BufferedReader bufReader = new BufferedReader(inReader);

            String line = null;
            while((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            Log.e("but", result.toString());
            bufReader.close();
            inReader.close();
            in.close();
        }

        catch(Exception e) { //エラー
            Log.e("button", e.getMessage());
        }

        return result.toString(); //onPostExecuteへreturn
    }

    @Override
    protected void onPostExecute(String result) { //doInBackgroundが終わると呼び出される
        try {
            JSONObject json = new JSONObject(result);
            String items = json.getString("items");
            JSONArray itemsArray = new JSONArray(items);
            JSONObject bookInfo = itemsArray.getJSONObject(0).getJSONObject("volumeInfo");

            String title = bookInfo.getString("title");
            String publishedDate = bookInfo.getString("publishedDate");

            TextView titleView = titleViewReference.get();
            TextView dateView = dateViewReference.get();

            titleView.setText(title);
            dateView.setText(publishedDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
