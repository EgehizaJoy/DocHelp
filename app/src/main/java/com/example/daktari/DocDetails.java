package com.example.daktari;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class DocDetails extends AppCompatActivity {

    HttpResponse httpResponse;
    Button button;
    TextView textView,textusername,expertise,gender,proposal;
    JSONObject jsonObject = null ;
    JSONObject obj = null ;
    String StringHolder = "" ;
    ProgressBar progressBar;

    RequestParams params = new RequestParams();
    // Adding HTTP Server URL to string variable.
   // https://daktari.000webhostapp.com/
    String HttpURL = "https://daktari.000webhostapp.com/DaktariPDO/doctors.php";

    String finalResult,strusername;
    HashMap<String,String> hashMap=new HashMap<>();
    HttpParse httpParse=new HttpParse();
    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_details);

        // Assigning ID's to button, textView and progressbar.
        expertise=findViewById(R.id.textViewemail);
        gender=findViewById(R.id.textViewmobile);
        proposal=findViewById(R.id.textViewproposal);
        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        textusername=findViewById(R.id.textusername);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        // Adding click lister to button.
        final String tempholder=getIntent().getStringExtra("doctorclickedvalue");
        textusername.setText(tempholder);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress bar on button click.
                progressBar.setVisibility(View.VISIBLE);

                //Calling GetDataFromServerIntoTextView method to Set JSon MySQL data into TextView.
                new GetDataFromServerIntoTextView(DocDetails.this).execute();
            }
        });

        toolbar=getSupportActionBar();
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_post){
                    Intent intent=new Intent(DocDetails.this, Post.class);
                    startActivity(intent);
                }
                else  if(item.getItemId()==R.id.action_viewposts){
                    Intent intent=new Intent(DocDetails.this,HomeActivity.class);
                    startActivity(intent);
                }
                else  if(item.getItemId()==R.id.action_home){
                    Intent intent=new Intent(DocDetails.this,WelcomeScreen.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }


    // Declaring GetDataFromServerIntoTextView method with AsyncTask.
    public class GetDataFromServerIntoTextView extends AsyncTask<Void, Void, Void>
    {
        // Declaring CONTEXT.
        public Context context;


        public GetDataFromServerIntoTextView(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                HttpClient httpClient = new DefaultHttpClient();
                //HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                final String tempholder=getIntent().getStringExtra("doctorclickedvalue");
                // Adding HttpURL to my HttpPost oject.
                HttpPost httpPost = new HttpPost("https://daktari.000webhostapp.com/DaktariPDO/doctors.php");

                httpResponse = httpClient.execute(httpPost);
                //connection.setRequestMethod("GET");

                StringHolder = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                // Passing string holder variable to JSONArray.
                JSONArray jsonArray = new JSONArray(StringHolder);
                jsonObject = jsonArray.getJSONObject(0);

                for (int i=0;i<jsonArray.length();i++){
                    obj=jsonArray.getJSONObject(i);
                }

            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try {
                // Adding JSOn string to textview after done loading.
                    textView.setText("Full Name:"+jsonObject.getString("fullname"));
                    expertise.setText("Expertise:"+jsonObject.getString("expertise"));
                    gender.setText("Gender:"+jsonObject.getString("gender"));
                    proposal.setText("proposal:"+jsonObject.getString("proposal"));
                    Button button1=findViewById(R.id.messagedoc);
                    button1.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                Toast.makeText(context, "Not connected..", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            //Hiding progress bar after done loading TextView.
            progressBar.setVisibility(View.GONE);

        }
    }
    public void MessageDoctor(View view){
        Intent intent=new Intent(DocDetails.this,Chat.class);
        startActivity(intent);
    }

}

