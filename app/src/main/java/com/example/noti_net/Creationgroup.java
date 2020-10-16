package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Creationgroup extends AppCompatActivity
{

    public static ArrayList<String> group = new ArrayList<String>();
    public static ArrayList<String> participantGroup = new ArrayList<String>();
    public static String user = "";
    private EditText identificationUser;
    private TextView groupUser;
    private EditText nameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creationgroup);
        nameGroup = findViewById(R.id.nameGroup);
        identificationUser = findViewById(R.id.identificationUser);
        groupUser = findViewById(R.id.listUser);
        groupUser.setText("");
    }

    public void addUser(View view)
    {
        if(!user.equals(""))
        {
            user = user + "\n" + identificationUser.getText().toString();
        }
        else
        {
           user = identificationUser.getText().toString();
        }
        groupUser.setText(user);
        identificationUser.setText("");
    }

    public void createGroup(View view)
    {
        group.add(nameGroup.getText().toString());
        participantGroup.add(user);
        user = "";
        Toast.makeText(this, "Grupo creado con éxito", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Creationgroup.this, MainActivity.class);
        startActivity(intent);
    }
}