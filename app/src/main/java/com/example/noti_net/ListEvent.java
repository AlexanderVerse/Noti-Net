package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListEvent extends AppCompatActivity
{

    public static ProgressBar progressBar;
    public static Integer counter = 1;
    public TextView step;
    public TextView work;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        work = findViewById(R.id.work);
        work.setText(CreationEvent.nameEvent.get(0));
        step = findViewById(R.id.step);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(CreationEvent.numberStepEvent.get(0));
        counter = 1;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
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