package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpecificGroup extends AppCompatActivity {

    private Intent intent;
    private String nameGroup;
    private String phoneNumber;
    private RequestQueue queue;
    private String urlServer;
    private int statusResponse;
    private String routeGetTask;
    private TextView nameEditT;
    private TextView dateEditT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_group);
        nameEditT = (TextView) findViewById(R.id.name);
        dateEditT = (TextView) findViewById(R.id.date);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nameEditT.setText("Hola, " + DataConfig.getName() + "!");
        dateEditT.setText("Tus tareas de hoy " + dateFormat.format(date));
        intent = getIntent();
        nameGroup = intent.getStringExtra("nameGroup");
        phoneNumber = intent.getStringExtra("phoneNumber");
        urlServer = DataConfig.getServerURL();
        routeGetTask = "/getTask?";
        //getTask();
    }

    private void getTask()
    {
        queue = Volley.newRequestQueue(this);
        routeGetTask += "nameGroup=" + nameGroup;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, routeGetTask, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            if(statusResponse != 202)
                            {
                                Toast.makeText(SpecificGroup.this, response.getString("mensaje"),
                                        Toast.LENGTH_LONG).show();
                                //paintPictureGroups(response.getJSONArray("estructura"));
                            }
                            else
                            {
                                System.out.println("No hay coincidencias: " + response.getString(
                                        "mensaje"));
                            }
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(SpecificGroup.this, "No hay coincidencias con lo que busca",
                                    Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusResponse = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
    }

    /*private void paintTextViewIntegrant(JSONArray struct) throws JSONException {
        JSONObject jsonObject;
        jsonObject = struct.getJSONObject(x);
        JSONArray = jsonObject.getJSONArray("");
        for (int x = 0; x < struct.length(); x++)
        {

            TextView textView = new TextView();
            textView.setText();
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextSize(2, 11);
            LinearLayout.LayoutParams lay2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearIntegrant.addView(row2, lay2);
        }
    }*/

    public void openViewTask(View view)
    {
         Intent intent = new Intent(SpecificGroup.this, CreationEvent.class);
         startActivity(intent);
    }
}