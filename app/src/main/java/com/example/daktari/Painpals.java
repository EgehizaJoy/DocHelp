package com.example.daktari;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Painpals extends AppCompatActivity {

    String stringholder="";
    String listfullname;
    TextView text;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painpals);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        connect();
    }
    public void connect(){
        String data;
        final List<String> r=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,r);
        list=(ListView)findViewById(R.id.listView2);
        try{
            DefaultHttpClient client=new DefaultHttpClient();
            HttpGet request=new HttpGet("https://daktari.000webhostapp.com/DaktariPDO/painpals.php");
            HttpResponse response=client.execute(request);
            HttpEntity entity=response.getEntity();
            data= EntityUtils.toString(entity);
            Log.e("STRING",data);
            try{

                JSONArray json=new JSONArray(data);
                for (int i=0;i<json.length();i++){
                    JSONObject obj=json.getJSONObject(i);
                    String username=obj.getString("Username");
                    Log.e("STRING",username);
                    r.add(username);
                    list.setAdapter(adapter);
                }
                final int size=r.size();
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        //Toast.makeText(Doctors.this,r.get(position), Toast.LENGTH_SHORT).show();
                        for (int i=0;i<size;i++){
                            if(position==i){
                                Toast.makeText(Painpals.this,r.get(position), Toast.LENGTH_SHORT).show();
                               listfullname=r.get(position);
                                DemoClass.palsnames=r.get(position);
                                Intent intent=new Intent(Painpals.this,PainpalsPosts.class);
                                intent.putExtra("clickedvalue",listfullname);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }catch (JSONException e){
                e.printStackTrace();
            }
        }catch (ClientProtocolException e){
            Log.d("Doctors",e.getLocalizedMessage());
        }catch (IOException e){
            Log.d("Doctors",e.getLocalizedMessage());
        }
    }

}
