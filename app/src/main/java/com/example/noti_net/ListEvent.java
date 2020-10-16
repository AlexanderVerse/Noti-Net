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
    public TextView step;
    public TextView event;
    public TextView porcentage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        event = findViewById(R.id.event);
        event.setText(CreationEvent.nameEvent.get(0));
        step = findViewById(R.id.activity);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(CreationEvent.numberStepEvent.get(0));
        counter = 1;
        porcent = 100 / CreationEvent.numberStepEvent.get(0);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        porcentage = findViewById(R.id.percentage);
        porcentage.setText(0);

        new MyAsyncTask().execute(CreationEvent.numberStepEvent.get(0));
        
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
                    Thread.sleep(10000);
                    step.setText(CreationEvent.pasosExtra[counter - 1]);
                    publishProgress(ListEvent.counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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