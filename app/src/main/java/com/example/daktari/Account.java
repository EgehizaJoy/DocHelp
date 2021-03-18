package com.example.daktari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
    }
    public void LogIn(View v){
        startActivity(new Intent(Account.this, Login.class));
    }
    public void signUpasPatient(View v)
    {
        startActivity(new Intent(Account.this, Register.class));
    }
    public void signUpasDoctor(View v)
    {
        startActivity(new Intent(Account.this, RegisterDoc.class));
    }
    public void s(View v){

    }
}


