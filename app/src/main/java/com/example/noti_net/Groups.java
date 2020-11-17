package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Groups extends AppCompatActivity
{

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private TextView nameEditT;
    private TextView dateEditT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        nameEditT = (TextView) findViewById(R.id.name);
        dateEditT = (TextView) findViewById(R.id.date);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nameEditT.setText("Hola, " + DataConfig.getName() + "!");
        dateEditT.setText("Tus tareas de hoy " + dateFormat.format(date));
    }

    public void createGroup(View view)
    {
        Intent intent = new Intent(Groups.this, Creationgroup.class);
        startActivity(intent);
    }
}