package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CreationEvent extends AppCompatActivity {

    private Spinner listCountryCode;
    private Spinner listUser;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterUser;
    public static ArrayList<Timer> eventosTiempo =  new ArrayList<Timer>();
    public static ArrayList<Integer> numberStepEvent = new ArrayList<Integer>();
    public static ArrayList<Integer> conteoEvento = new ArrayList<Integer>();
    public static ArrayList<String> nameEvent = new ArrayList<String>();
    public String steps = "";
    public TextView viewSteps;
    public EditText step;
    public Intent intent;
    public EditText work;
    public static String pasosExtra[];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_event);

        work = findViewById(R.id.work);
        step = findViewById(R.id.steps);
        viewSteps = findViewById(R.id.viewSteps);
        String[] group = new String[Creationgroup.group.size()];
        String[] groupUser = new String[Creationgroup.participantGroup.size()];
        for (int x = 0; x < Creationgroup.group.size(); x++)
        {
            group[x] = Creationgroup.group.get(x);
        }
        for (int x = 0; x < Creationgroup.participantGroup.size(); x++)
        {
            groupUser[x] = Creationgroup.participantGroup.get(x);
        }
        adapter = new ArrayAdapter<String>(this, R.layout.namegroup, group);
        adapterUser = new ArrayAdapter<String>(this, R.layout.namegroup, groupUser);
        listCountryCode = (Spinner) findViewById(R.id.namesGroup);
        listCountryCode.setAdapter(adapter);
        listUser = (Spinner) findViewById(R.id.listUser);
        listUser.setAdapter(adapterUser);
    }


    public void addSteps(View view)
    {
        if(!steps.equals(""))
        {
            steps = steps + ", " + step.getText().toString();
        }
        else
        {
            steps = step.getText().toString();
        }
        viewSteps.setText(steps);
        step.setText("");
    }

    public void createEvent(View view)
    {
        conteoEvento.add(0);
        String pasos = viewSteps.getText().toString();
        pasosExtra = pasos.split(",");
        numberStepEvent.add(pasosExtra.length);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {

                System.out.println(numberStepEvent.get(0) + ": A Kiss every" +
                        " 5 " +
                        "seconds: " + conteoEvento.get(0));
                if(conteoEvento.get(0) == numberStepEvent.get(0) && conteoEvento.get(0) != 0)
                {
                    eventosTiempo.get(0).cancel();
                }
                conteoEvento.set(0, conteoEvento.get(conteoEvento.size() - 1) + 1);
            }       
        },0,5000);
        eventosTiempo.add(t);
        nameEvent.add(work.getText().toString());
        intent = new Intent(CreationEvent.this, ListEvent.class);
        startActivity(intent);
    }

}

