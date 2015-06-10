package nguyenngoclam.cellphones;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by GIO on 6/9/2015.
 */
public class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
    Activity contextCha;
    private static ArrayList<ListItem> listData = new ArrayList<ListItem>();
    private static String local;
    public ProgressDialog progressDia;
    public ReadJSONFeedTask(Activity ctx)
    {
        contextCha=ctx;
    }

    public static ArrayList<ListItem> getListData() {
        return listData;
    }

    public static void setListData(ArrayList<ListItem> listData) {
        ReadJSONFeedTask.listData = listData;
    }

    public static String getLocal() {
        return local;
    }

    public static void setLocal(String local) {
        ReadJSONFeedTask.local = local;
    }

    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        //Toast.makeText(contextCha, "onPreExecute!",
               // Toast.LENGTH_LONG).show();
        listData.clear();
        progressDia = new ProgressDialog(contextCha);
        progressDia.setMessage("Load data..");
        progressDia.show();
    }
    protected String doInBackground(String... urls) {
        return readJSONFeed(urls[0]);
    }
    protected void onPostExecute(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            listData.clear();
            Log.i("JSON", "Number of surveys in feed: " +
                    jsonArray.length());

            //---print out the content of the json feed---
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Toast.makeText(getBaseContext(), jsonObject.getString("image") + " - " + jsonObject.getString("name"),
                //Toast.LENGTH_SHORT).show();
                ListItem newsData = new ListItem();
                newsData.setUrl(jsonObject.getString("image"));
                newsData.setName(jsonObject.getString("name"));
                newsData.setPrice(jsonObject.getString("price"));
                newsData.setAva(jsonObject.getString("ava"));
                newsData.setLinks(jsonObject.getString("url"));
                newsData.setColor(jsonObject.getString("color"));
                listData.add(newsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressDia.cancel();
        final ListView listView = (ListView) contextCha.findViewById(R.id.custom_list);
        listView.setAdapter(new CustomListAdapter(contextCha, listData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem newsData = (ListItem) listView.getItemAtPosition(position);
                // Toast.makeText(MainActivity.this, newsData.getUrl(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(contextCha);
                alert.setTitle(newsData.getName());

                WebView wv = new WebView(contextCha);
                wv.loadUrl(newsData.getLinks());
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } else {
                Log.e("JSON", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}