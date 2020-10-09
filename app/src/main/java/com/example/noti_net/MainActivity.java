package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openViewRegisterGroup(View view)
    {
        intent = new Intent(MainActivity.this, Creationgroup.class);
        startActivity(intent);
    }

    public void openViewRegisterEvent(View view)
    {
        intent = new Intent(MainActivity.this, CreationEvent.class);
        startActivity(intent);
    }

    public void openViewListEvent(View view)
    {
        intent = new Intent(MainActivity.this, ListEvent.class);
        startActivity(intent);
    }
    
}