package com.example.daktari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText etusername;
    EditText etpassword;
    Button login;
    String passwordholder,usernameholder;
    String finalResult,strusername;
    String HttpUrl="https://daktari.000webhostapp.com/DaktariPDO/allsers.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap=new HashMap<>();
    HttpParse httpParse=new HttpParse();
    private static final String TAG="Login.java";
    public static final String UserName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
       //storing sessions
        //initialize the variables
        etusername=(EditText)findViewById(R.id.loginusername);
        etpassword=(EditText)findViewById(R.id.loginpassword);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    UserLoginFunction(usernameholder,passwordholder);
                    //strusername=etusername.getText().toString();
                    //insertData(strusername);
                    DemoClass.username=etusername.getText().toString();
                    DemoClass.demopass=etpassword.getText().toString();
                }
                else{
                    Toast.makeText(Login.this, "please fill all form fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){
        usernameholder=etusername.getText().toString();
        passwordholder=etpassword.getText().toString();
        if(TextUtils.isEmpty(usernameholder)||TextUtils.isEmpty(passwordholder)){
            CheckEditText=false;
        }
        else{
            CheckEditText=true;
        }
    }
    public void UserLoginFunction(final String username,final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=ProgressDialog.show(Login.this,"",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("data matched")){
                    finish();
                    Intent intent=new Intent(Login.this,WelcomeScreen.class);
                    intent.putExtra(username,password);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                finalResult=httpParse.postRequest(hashMap,HttpUrl);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass=new UserLoginClass();
        userLoginClass.execute(username,password);
    }
    public void signUp(View v){
        startActivity(new Intent(Login.this, Account.class));
    }
}
