package com.example.daktari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterDoc extends AppCompatActivity {
    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;

    private DatabaseReference mdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doc);

        username = (EditText) findViewById(R.id.usernamedoc);
        password = (EditText) findViewById(R.id.passworddoc);
        registerButton = (Button) findViewById(R.id.registerButtondoc);
        login = (TextView) findViewById(R.id.logindoc);

        // mdatabaseref= FirebaseDatabase.getInstance().getReference("users");
        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterDoc.this, Login.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else if (!user.matches("[A-Za-z0-9]+")) {
                    username.setError("only alphabet or number allowed");
                } else if (user.length() < 5) {
                    username.setError("at least 5 characters long");
                } else if (pass.length() < 5) {
                    password.setError("at least 5 characters long");
                } else {
                    final ProgressDialog pd = new ProgressDialog(RegisterDoc.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://daktari-c4e9b.firebaseio.com/usersdoc.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://daktari-c4e9b.firebaseio.com/usersdoc");

                            if (s.equals("null")) {
                                reference.child(user).child("password").setValue(pass);
                                Toast.makeText(RegisterDoc.this, "registration successful", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        reference.child(user).child("password").setValue(pass);
                                        Toast.makeText(RegisterDoc.this, "registration successful", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(RegisterDoc.this,SignUpDoc.class);
                                        intent.putExtra("doctorusername",user);
                                        intent.putExtra("doctorpass",pass);
                                        DemoClass.firebaseusername=user;
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterDoc.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(RegisterDoc.this);
                    rQueue.add(request);
                }
            }
        });
    }
}