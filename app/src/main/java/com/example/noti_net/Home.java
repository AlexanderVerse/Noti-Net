package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity
{

    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private TextView nameEditT;
    private TextView dateEditT;
    private ArrayAdapter<String> adapter;
    private Spinner listDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.getDay();
        nameEditT = (TextView) findViewById(R.id.name);
        dateEditT = (TextView) findViewById(R.id.date);
        Intent intent = getIntent();
        nameEditT.setText("Hola, " + DataConfig.getName() + "!");
        dateEditT.setText("Tus tareas de hoy " + dateFormat.format(date));
        String[] arrayDate = {"Today"};
        adapter = new ArrayAdapter<String>(this, R.layout.listcountry, arrayDate);
        listDate = (Spinner) findViewById(R.id.listTaskDate);
        listDate.setAdapter(adapter);
        //listDate.setSelection(138);
    }

    public void viewGroups(View view)
    {
        Intent intent = new Intent(this, Groups.class);
        startActivity(intent);
    }

    public void viewProyects(View view)
    {
        Intent intent = new Intent(this, Proyects.class);
        startActivity(intent);
    }
}