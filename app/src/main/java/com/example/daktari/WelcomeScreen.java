package com.example.daktari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class WelcomeScreen extends AppCompatActivity {
    ActionBar toolbar;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap=new HashMap<>();
    HttpParse httpParse=new HttpParse();
    String HttpUrl="https://daktari.000webhostapp.com/DaktariPDO/delete.php";
    String finalResult,strusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);

        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_post) {
                    Intent intent = new Intent(WelcomeScreen.this, Post.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_viewposts) {
                    Intent intent = new Intent(WelcomeScreen.this, HomeActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_home) {
                    Intent intent = new Intent(WelcomeScreen.this, WelcomeScreen.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void Painpal(View view) {
        startActivity(new Intent(WelcomeScreen.this, Users.class));
    }

    public void doctors(View view) {
        startActivity(new Intent(WelcomeScreen.this, UsersDoc.class));
    }

    public void forums(View view) {
        startActivity(new Intent(WelcomeScreen.this, MainForum.class));
    }
    public void UserLoginFunction(final String username, final String password) {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(WelcomeScreen.this, "loading data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if (httpResponseMsg.equalsIgnoreCase("Data Matched")) {
                    finish();
                } else {
                    Toast.makeText(WelcomeScreen.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username", params[0]);
                hashMap.put("password", params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpUrl);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(username, password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        String myusername=DemoClass.username;
        String mypass=DemoClass.demopass;
        if(id==R.id.delete_account){
            UserLoginFunction(myusername,mypass);
        }
        if(id==R.id.edit_profile){
            Intent intent=new Intent(WelcomeScreen.this,EditProfile.class);
            startActivity(intent);
        }
        if(id==R.id.add_account){
            Intent intent=new Intent(WelcomeScreen.this,Account.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}