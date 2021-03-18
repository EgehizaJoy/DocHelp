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

public class EditProfile extends AppCompatActivity {
EditText musername,email,fullname,mobile;
Button edit;
String strusernsme,stremail,strfullname,strmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        musername=findViewById(R.id.editusername);
        email=findViewById(R.id.editemail);
        fullname=findViewById(R.id.editfullname);
        mobile=findViewById(R.id.editmobile);

        edit=findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username1=DemoClass.username;
                strusernsme=musername.getText().toString();
                stremail=email.getText().toString();
                strfullname=fullname.getText().toString();
                strmobile=mobile.getText().toString();
                insertData(stremail,strmobile,strfullname,strusernsme,username1);
            }
        });
    }

    public void insertData(final String email, final String mobile,final String fullname,final String username,final String username1){
        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {
                String emailholder=email;
                String mobileholder=mobile;
                String fullnameholder=fullname;
                String usernameholder=username;
                String usernameholder1=username1;

                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email",emailholder));
                nameValuePairs.add(new BasicNameValuePair("mobile",mobileholder));
                nameValuePairs.add(new BasicNameValuePair("fullname",fullnameholder));
                nameValuePairs.add(new BasicNameValuePair("username",usernameholder));
                nameValuePairs.add(new BasicNameValuePair("username1",usernameholder1));
                String link="https://daktari.000webhostapp.com/DaktariPDO/editprofile.php";
                URL url= null;
                try {
                    url = new URL(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try{
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    HttpPost httpPost=new HttpPost("https://daktari.000webhostapp.com/DaktariPDO/editprofile.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse=httpClient.execute(httpPost);
                    HttpEntity httpEntity=httpResponse.getEntity();
                    connection.setRequestMethod("POST");

                }catch (ClientProtocolException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Intent intent=new Intent(EditProfile.this,WelcomeScreen.class);
                startActivity(intent);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask=new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(email,mobile,fullname,username,username1);
    }
}
