package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        Intent intent = new Intent(Groups.this, newGroup.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent = new Intent(Groups.this, Home.class);
        startActivity(intent);
    }

    public void getGroups()
    {
        queue = Volley.newRequestQueue(this);
        String routeLogin = urlServer + "/createGroup";
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("phoneNumber", "2441475065");
        arguments.put("nameGroup", nameGroup.getText().toString());
        arguments.put("integrants", arrayUser);
        JSONObject argumentsBody = new JSONObject(arguments);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, routeLogin, argumentsBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (statusResponse == 200)
                        openView();
                    else
                        Toast.makeText(newGroup.this, response.getString("mensaje"),
                                Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    System.out.println("Error JSON: " + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(newGroup.this,
                        "Error en respuesta del servidor: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
            {
                statusResponse = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
    }
}