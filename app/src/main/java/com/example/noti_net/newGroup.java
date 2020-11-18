package com.example.noti_net;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class newGroup extends AppCompatActivity {

    private TextView groupUser;
    public static String users = "";
    private EditText userPhone;
    private EditText nameGroup;
    private int statusResponse;
    private RequestQueue queue;
    private String urlServer;
    private String arrayUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        groupUser = (TextView) findViewById(R.id.integrants);
        groupUser.setText("");
        userPhone = (EditText) findViewById(R.id.userPhone);
        nameGroup = (EditText) findViewById(R.id.nameGroup);
        urlServer = DataConfig.getServerURL();
    }

    public void addUser(View view)
    {
        if(!users.equals(""))
        {
            users = users + "\n- " + userPhone.getText().toString();
            arrayUser += ", ";
            arrayUser = arrayUser + "\"" + userPhone.getText().toString() + "\"";
        }
        else
        {
            arrayUser = "{\"array\": [";
            users = "- " + userPhone.getText().toString();
            arrayUser = arrayUser + "\"" + userPhone.getText().toString() + "\"";
        }
        groupUser.setText(users);
        userPhone.setText("");
    }

    public void createGroup(View view)
    {
        saveGroup();
    }

    private void saveGroup()
    {
        arrayUser = arrayUser + "]}";
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

    public void openView()
    {
        users = "";
        Toast.makeText(this, "Grupo creado con éxito", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(newGroup.this, Groups.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent = new Intent(newGroup.this, Groups.class);
        startActivity(intent);
    }
}