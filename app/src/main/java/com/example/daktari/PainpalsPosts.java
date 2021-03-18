package com.example.daktari;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PainpalsPosts extends AppCompatActivity {
EditText painpalpost;
Button trial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painpals_posts);

        final String tempholder=getIntent().getStringExtra("clickedvalue");
        trial=findViewById(R.id.buttontry);

        painpalpost.setText(tempholder);

        String strusername=painpalpost.getText().toString();
        insertData(strusername);

        trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String strusername=painpalpost.getText().toString();
                //insertData(strusername);
            }
        });

    }

    public void insertData(final String username){
        class SendPostReqAsyncTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... params) {
                String usernameholder=username;

                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username",usernameholder));

                String link="https://daktari.000webhostapp.com/DaktariPDO/painpalpost.php";
                URL url= null;
                try {
                    url = new URL(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try{
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    HttpPost httpPost=new HttpPost("https://daktari.000webhostapp.com/DaktariPDO/painpalpost.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse=httpClient.execute(httpPost);
                    HttpEntity httpEntity=httpResponse.getEntity();
                    connection.setRequestMethod("POST");

                }catch (ClientProtocolException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                return "data inserted successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(PainpalsPosts.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
               // Intent intent=new Intent(PainpalsPosts.this,WelcomeScreen.class);
                //startActivity(intent);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask=new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(username);
    }
}
