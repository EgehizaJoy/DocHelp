package com.example.daktari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

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
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doctors extends AppCompatActivity {
String stringholder="";
String listfullname;
TextView text;
ListView list;
    String url = "https://daktari.000webhostapp.com/DaktariPDO/jsontextviewa.php";


    HttpResponse httpResponse;
    Button button;
    TextView textView;
    JSONObject jsonObject = null ;
    String StringHolder = "" ;
    ProgressBar progressBar;
    RequestParams params = new RequestParams();
    // Adding HTTP Server URL to string variable.
    String HttpURL = "https://daktari.000webhostapp.com/DaktariPDO/doctors.php";

    String finalResult,strusername;
    HashMap<String,String> hashMap=new HashMap<>();
    HttpParse httpParse=new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        connect();
    }
    public void connect(){
        String data;
        final List<String>r=new ArrayList<String>();
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,r);
        list=(ListView)findViewById(R.id.listView1);
        try{
            DefaultHttpClient client=new DefaultHttpClient();
            HttpGet request=new HttpGet("https://daktari.000webhostapp.com/DaktariPDO/doctors.php");
            HttpResponse response=client.execute(request);
            HttpEntity entity=response.getEntity();
            data= EntityUtils.toString(entity);
            Log.e("STRING",data);
            try{

                JSONArray json=new JSONArray(data);
                for (int i=0;i<json.length();i++){
                    JSONObject obj=json.getJSONObject(i);
                    String fullname=obj.getString("fullname");
                    Log.e("STRING",fullname);
                    r.add(fullname);
                    list.setAdapter(adapter);
                }
                final int size=r.size();
               listfullname=r.get(2);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        //Toast.makeText(Doctors.this,r.get(position), Toast.LENGTH_SHORT).show();
                        for (int i=0;i<size;i++){
                            if(position==i){
                                //Toast.makeText(Doctors.this,r.get(position), Toast.LENGTH_SHORT).show();
                                listfullname=r.get(position);
                                DemoClass.docusername=listfullname;
                                //insertData(r.get(position));
                                //DemoClass.docusername=r.get(position);


                                //sending selected option to php file
                                RequestQueue queue = Volley.newRequestQueue(Doctors.this);
                                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        Toast.makeText(Doctors.this, "dfdsfsd"+response, Toast.LENGTH_SHORT).show();
                                        Log.i("My success",""+response);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(Doctors.this, "my error :"+error, Toast.LENGTH_LONG).show();
                                        Log.i("My error",""+error);
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String,String> map = new HashMap<String, String>();
                                        map.put("fullname",listfullname);

                                        return map;
                                    }
                                };
                                queue.add(request);

                                Intent intent=new Intent(Doctors.this,DocDetails.class);
                                intent.putExtra("docclickedvalue",listfullname);
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
