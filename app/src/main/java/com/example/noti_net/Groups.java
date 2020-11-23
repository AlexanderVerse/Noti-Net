package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private int statusResponse;
    private RequestQueue queue;
    private String urlServer;
    private String routeGroups;
    private GridLayout gridGroups;
    private LinearLayout linearIntegrant;
    private ImageView[] groupsImage;
    private String[] nameImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        urlServer = DataConfig.getServerURL();
        nameEditT = (TextView) findViewById(R.id.name);
        dateEditT = (TextView) findViewById(R.id.date);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        nameEditT.setText("Hola, " + DataConfig.getName() + "!");
        dateEditT.setText("Tus tareas de hoy " + dateFormat.format(date));
        routeGroups = urlServer + "/getGroups?";
        gridGroups = (GridLayout) findViewById(R.id.gridLayoutGroups);
        linearIntegrant = (LinearLayout) findViewById(R.id.linearLayoutIntegrants);
        getGroups();
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

    private void getGroups()
    {
        queue = Volley.newRequestQueue(this);
        routeGroups += "phoneNumber=" + DataConfig.getPhoneNumber();
        //routeGroups += "phoneNumber=2441475065";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, routeGroups, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            if(statusResponse != 202)
                            {
                                Toast.makeText(Groups.this, response.getString("mensaje"),
                                        Toast.LENGTH_LONG).show();
                                paintPictureGroups(response.getJSONArray("estructura"));
                            }
                            else
                            {
                                System.out.println("No hay coincidencias: " + response.getString(
                                        "mensaje"));
                            }
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(Groups.this, "No hay coincidencias con lo que busca",
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

    private void paintPictureGroups(JSONArray struct)
    {
        assignPropertiesImage(struct);
        gridGroups.removeAllViews();
        int total = struct.length();
        int indexImage = 0;
        int column;
        gridGroups.setRowCount(2);
        System.out.print(total);
        if(total == 1)
        {
            column = 1;
            gridGroups.setColumnCount(1);
        }
        else
        {
            column = 2;
            gridGroups.setColumnCount(2);
        }
        for (int y = 0; y < column; y++)
        {
            GridLayout.LayoutParams paramGridLayout =
                new GridLayout.LayoutParams();
            paramGridLayout.leftMargin = 100;
            //paramGridLayout.rightMargin = 10;
            paramGridLayout.topMargin = 5;
            paramGridLayout.columnSpec = GridLayout.spec(y);
            paramGridLayout.rowSpec = GridLayout.spec(0);
            groupsImage[y].setLayoutParams(paramGridLayout);
            gridGroups.addView(groupsImage[y]);
        }

        
        for (int z = 0; z < column; z++)
        {
            TextView nameGroup = new TextView(this);
            nameGroup.setText(nameImage[z]);
            nameGroup.setTextSize(18);
            GridLayout.LayoutParams paramGridLayout =
                    new GridLayout.LayoutParams();
            paramGridLayout.leftMargin = 150;
            //paramGridLayout.rightMargin = 10;
            paramGridLayout.topMargin = 5;
            paramGridLayout.columnSpec = GridLayout.spec(z);
            paramGridLayout.rowSpec = GridLayout.spec(1);
            nameGroup.setLayoutParams (paramGridLayout);
            gridGroups.addView(nameGroup);
        }
        createListenerGroups(struct);
    }

    private void assignPropertiesImage(JSONArray struct)
    {
        groupsImage = new ImageView[struct.length()];
        nameImage = new String[struct.length()];
        try
        {
            for (int x = 0; x < groupsImage.length; x++)
            {
                groupsImage[x] = new ImageView(this);
                groupsImage[x].setImageResource(R.drawable.ic_launcher_foreground);
                nameImage[x] = struct.getString(x);
            }
        }
        catch (JSONException e)
        {
            System.out.println("Error n obtener estrucutrua de uan posición del arreglo: " + e);
        }
    }
    

    private void createListenerGroups(JSONArray struct)
    {
       for (int i = 0; i < struct.length(); i++)
       {
           final int finalX = i;
           groupsImage[finalX].setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v)
               {
                   Toast.makeText(Groups.this, "Tome captura de imagen: " + finalX,
                           Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(Groups.this, SpecificGroup.class);
                   intent.putExtra("nameGroup", nameImage[finalX]);
                   intent.putExtra("phoneNumber", DataConfig.getPhoneNumber());
                   //intent.putExtra("phoneNumber", DataConfig.getPhoneNumber());
                   startActivity(intent);
               }
           });
       }
    }
    
}