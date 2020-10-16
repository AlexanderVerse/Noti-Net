package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ListEvent extends AppCompatActivity
{

    public static ProgressBar progressBar;
    public static Integer counter = 1;
    public static Integer porcent;
    public static int numRandom;
    public TextView step;
    public TextView event;
    public TextView porcentage;
    public TextView currentActivity;
    public TextView performedActivity;
    public TextView observations;
    public String performedActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        observations = findViewById(R.id.observation);
        currentActivity = findViewById(R.id.currentActivity);
        performedActivity = findViewById(R.id.performedActivity);
        event = findViewById(R.id.event);
        event.setText(CreationEvent.nameEvent.get(0));
        step = findViewById(R.id.currentActivity);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(CreationEvent.numberStepEvent.get(0));
        counter = 1;
        porcent = 100 / CreationEvent.numberStepEvent.get(0);
        numRandom = (int)(Math.random() * (CreationEvent.pasosExtra.length + 1) + 1);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        porcentage = findViewById(R.id.percentage);
        porcentage.setText("0");
        currentActivity.setText(CreationEvent.pasosExtra[0]);
        System.out.println("Retraso en la actividad: " + numRandom);
        new MyAsyncTask().execute(CreationEvent.numberStepEvent.get(0));
    }


    public void showToast()
    {
        Toast.makeText(ListEvent.this,"HUBO UN RETRASO EN LA TAREA: " + CreationEvent.pasosExtra[ListEvent.counter - 1], Toast.LENGTH_LONG).show();
    }


    class MyAsyncTask extends AsyncTask<Integer, Integer, String>
    {
        @Override
        protected String doInBackground(Integer... params)
        {
            for (; ListEvent.counter <= CreationEvent.numberStepEvent.get(0); ListEvent.counter++)
            {
                try
                {
                    if (ListEvent.numRandom == ListEvent.counter)
                    {
                        Thread.sleep(20000);
                        observations.setText("Retraso en la actividad: " + CreationEvent.pasosExtra[ListEvent.counter - 1] + ", se aumentaron 10 segundos mas\n");
                    }
                    else
                    {
                        Thread.sleep(10000);
                    }
                    porcentage.setText(String.valueOf(porcent * (ListEvent.counter)));
                    publishProgress(ListEvent.counter);

                    if (ListEvent.counter > 1 && ListEvent.counter < CreationEvent.numberStepEvent.get(0))
                    {
                        step.setText(CreationEvent.pasosExtra[counter]);
                        performedActivities =
                                performedActivities + "\n" + CreationEvent.pasosExtra[ListEvent.counter - 1];
                    }
                    else if(ListEvent.counter == 1)
                    {
                        step.setText(CreationEvent.pasosExtra[counter]);
                        performedActivities = " " + CreationEvent.pasosExtra[ListEvent.counter - 1];
                    }
                    else
                    {
                        performedActivities =
                                performedActivities + "\n" + CreationEvent.pasosExtra[ListEvent.counter - 1];
                        step.setText("");
                        porcentage.setText("100");
                    }
                    performedActivity.setText(performedActivities);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e);
                    e.printStackTrace();
                }
                catch (Exception e) { System.out.println(e); }
            }

            return "Tarea completa!. =)";
        }
        
        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(ListEvent.this, "Evento terminado con éxito", Toast.LENGTH_LONG).show();
            //ListEvent.progressBar.setVisibility(View.GONE);
        }
        
        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            ListEvent.progressBar.setProgress(values[0]);
        }
    }
}