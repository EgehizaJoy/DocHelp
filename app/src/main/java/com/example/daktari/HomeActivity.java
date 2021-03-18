package com.example.daktari;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private String URLstring = "https://daktari.000webhostapp.com/DaktariPDO/displayimages.php";
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<DataModel> dataModelArrayList;
    private ListAdapter listAdapter;
    RequestParams params = new RequestParams();
    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.lv);

        retrieveJSON();

        toolbar=getSupportActionBar();
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_post){
                    Intent intent=new Intent(HomeActivity.this, Post.class);
                    startActivity(intent);
                }
                else  if(item.getItemId()==R.id.action_viewposts){
                    Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else  if(item.getItemId()==R.id.action_home){
                    Intent intent=new Intent(HomeActivity.this,WelcomeScreen.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    private void retrieveJSON() {
        showSimpleProgressDialog(this, "Loading...","",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            //JSONArray jarr = new JSONArray(response);
                            //JSONObject obj = jarr.getJSONObject(0);
                            JSONObject obj = new JSONObject(response);

                            //if(obj.optString("status").equals("true")){

                               dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("result");
                                for (int i = 0; i < dataArray.length(); i++) {

                                    DataModel playerModel = new DataModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    //dataobj.put("caption",DemoClass.palsnames);
                                    playerModel.setCaption(dataobj.getString("AndroidNames"));
                                    playerModel.setImgURL(dataobj.getString("ImagePath"));
                                    playerModel.setusername(dataobj.getString("usernames"));

                                    dataModelArrayList.add(playerModel);

                                }

                                setupListview();

                            //}

                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private void setupListview(){
        removeSimpleProgressDialog();  //will remove progress dialog
        listAdapter = new ListAdapter(this, dataModelArrayList);
        listView.setAdapter(listAdapter);
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


