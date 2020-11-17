package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signIn(View view)
    {
        finish();
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void signUp(View view)
    {
        finish();
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    
}