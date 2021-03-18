package com.example.daktari;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.List;

public class SignUpDoc extends AppCompatActivity {
    RadioGroup gender,areaofexpertise;
    RadioButton male,female,depression,bipolar;
    EditText etemail,etmobile,etfullname,etusername,etpassword,etretype,etproposal;
    Button signup;
    String stremail,strmobile,strfullname,strusername,strpassword,strmale,strfemale,strdepression,strbipolar,strdate,strproposal;
private static final String TAG="SignUpDoc";
TextView mDisplayDate;
private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set strict mode policy
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_sign_up_doc);

        mDisplayDate=(TextView)findViewById(R.id.txtViewdate);
        gender=(RadioGroup)findViewById(R.id.gender);
        areaofexpertise=(RadioGroup)findViewById(R.id.areaofexpertise);
        male=(RadioButton)findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.female);
        bipolar=(RadioButton)findViewById(R.id.bipolar);
        depression=(RadioButton)findViewById(R.id.depression);
        etproposal=(EditText)findViewById(R.id.proposal);
        etemail=(EditText)findViewById(R.id.email);
        etmobile=(EditText)findViewById(R.id.mobile);
        etfullname=(EditText)findViewById(R.id.fullname);
        etusername=(EditText)findViewById(R.id.username);
        etpassword=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.signup);
        final String tempholder=getIntent().getStringExtra("doctorusername");
        etusername.setText(tempholder);
        final String tempholder1=getIntent().getStringExtra("doctorpass");
        etpassword.setText(tempholder1);

        DemoClass.regusername=etusername.getText().toString();
        DemoClass.regemail=etemail.getText().toString();
        DemoClass.regmobile=etmobile.getText().toString();
        DemoClass.regfullname=etfullname.getText().toString();

        mDisplayDate=(TextView)findViewById(R.id.txtViewdate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(SignUpDoc.this,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyy:"+month+"/"+day+"/"+year);

                String date=month+"/"+day+"/"+year;
                mDisplayDate.setText(date);
            }
        };

        //set up onclick listener for button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strproposal=etproposal.getText().toString();
                strdate=mDisplayDate.getText().toString();
                strmale=male.getText().toString();
                strfemale=female.getText().toString();
                strdepression=bipolar.getText().toString();
                strbipolar=depression.getText().toString();
                stremail=etemail.getText().toString();
                strmobile=etmobile.getText().toString();
                strfullname=etfullname.getText().toString();
                strusername=etusername.getText().toString();
                strpassword=etpassword.getText().toString();
                insertData(stremail,strmobile,strfullname,strusername,strpassword,strdate,strmale,strfemale,strdepression,strbipolar,strproposal);
            }
        });
    }
    public void insertData(final String email, final String mobile,final String fullname,final String username,final String password,final String date,final String male,final String female,final String depression,final String bipolar,final String proposal){
        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {
                String emailholder=email;
                String mobileholder=mobile;
                String fullnameholder=fullname;
                String usernameholder=username;
                String passwordholder=password;
                String dateholder=date;
                String maleholder=male;
                String femaleholder=female;
                String depressionholder=depression;
                String bipolarholder=bipolar;
                String proposalholder=proposal;

                List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email",emailholder));
                nameValuePairs.add(new BasicNameValuePair("mobile",mobileholder));
                nameValuePairs.add(new BasicNameValuePair("fullname",fullnameholder));
                nameValuePairs.add(new BasicNameValuePair("username",usernameholder));
                nameValuePairs.add(new BasicNameValuePair("password",passwordholder));
                nameValuePairs.add(new BasicNameValuePair("date",dateholder));
                nameValuePairs.add(new BasicNameValuePair("male",maleholder));
                nameValuePairs.add(new BasicNameValuePair("female",femaleholder));
                nameValuePairs.add(new BasicNameValuePair("depression",depressionholder));
                nameValuePairs.add(new BasicNameValuePair("bipolar",bipolarholder));
                nameValuePairs.add(new BasicNameValuePair("proposal",proposalholder));
                String link="https://daktari.000webhostapp.com/DaktariPDO/doctorsSignup.php";
                URL url= null;
                try {
                    url = new URL(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try{
                    HttpClient httpClient=new DefaultHttpClient();
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    HttpPost httpPost=new HttpPost("https://daktari.000webhostapp.com/DaktariPDO/doctorsSignup.php");
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
                Toast.makeText(SignUpDoc.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignUpDoc.this,WelcomeScreen.class);
                startActivity(intent);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask=new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(email,mobile,fullname,username,password,date,male,female,depression,bipolar,proposal);
    }

    public void signIn(View view){
        Intent intent=new Intent(SignUpDoc.this,Login.class);
        startActivity(intent);
    }
}
