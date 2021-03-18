package com.example.daktari;

import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
EditText etemail,etmobile,etfullname,etusername,etpassword,etretype;
Button signup;
String stremail,strmobile,strfullname,strusername,strpassword,strretype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up strictmode policy
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.signup);

        etemail=(EditText)findViewById(R.id.email);
        etmobile=(EditText)findViewById(R.id.mobile);
        etfullname=(EditText)findViewById(R.id.fullname);
        etusername=(EditText)findViewById(R.id.username);
        etpassword=(EditText)findViewById(R.id.password);
        etretype=(EditText)findViewById(R.id.retype);
        signup=(Button)findViewById(R.id.signup);
        final String tempholder=getIntent().getStringExtra("patientusername");
        etusername.setText(tempholder);
        final String tempholder1=getIntent().getStringExtra("patientpass");
        etpassword.setText(tempholder1);

        //set up onclick listener for button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stremail=etemail.getText().toString();
                strmobile=etmobile.getText().toString();
                strfullname=etfullname.getText().toString();
                strusername=etusername.getText().toString();
                strpassword=etpassword.getText().toString();
                strretype=etretype.getText().toString();
                insertData(stremail,strmobile,strfullname,strusername,strpassword,strretype);
            }
        });
    }
    public void LogIn(View v){
      Intent intent=new Intent(SignUp.this,Login.class);
      startActivity(intent);
    }
    public void insertData(final String email, final String mobile,final String fullname,final String username,final String password,final String retype){
        class SendPostReqAsyncTask extends AsyncTask<String,Void,String>{

         @Override
         protected String doInBackground(String... params) {
             String emailholder=email;
             String mobileholder=mobile;
             String fullnameholder=fullname;
             String usernameholder=username;
             String passwordholder=password;
             String retypeholder=retype;

             List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair("email",emailholder));
             nameValuePairs.add(new BasicNameValuePair("mobile",mobileholder));
             nameValuePairs.add(new BasicNameValuePair("fullname",fullnameholder));
             nameValuePairs.add(new BasicNameValuePair("username",usernameholder));
             nameValuePairs.add(new BasicNameValuePair("password",passwordholder));
             nameValuePairs.add(new BasicNameValuePair("retype",retypeholder));
             String link="https://daktari.000webhostapp.com/DaktariPDO/daktarimain.php";
             URL url= null;
             try {
                 url = new URL(link);
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             }
             try{
                 HttpClient httpClient=new DefaultHttpClient();
                 HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                 HttpPost httpPost=new HttpPost("https://daktari.000webhostapp.com/DaktariPDO/daktarimain.php");
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
             Toast.makeText(SignUp.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
             Intent intent=new Intent(SignUp.this,WelcomeScreen.class);
             startActivity(intent);
         }
     }
     SendPostReqAsyncTask sendPostReqAsyncTask=new SendPostReqAsyncTask();
     sendPostReqAsyncTask.execute(email,mobile,fullname,username,password,retype);
    }

}


