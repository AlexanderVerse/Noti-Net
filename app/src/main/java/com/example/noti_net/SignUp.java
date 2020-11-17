package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity
{

    private EditText name;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;
    private Button signUp;
    private RequestQueue queue;
    private int statusResponse;
    private String routeSignUp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.signUp);
        DataConfig dataConfig = new DataConfig();
        routeSignUp = dataConfig.getUrlServer() + "/signUp";
    }

    public void signUp(View view)
    {
        signUp.setEnabled(false);
        queue = Volley.newRequestQueue(this);
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("phoneNumber", phoneNumber.getText().toString());
        arguments.put("email", email.getText().toString());
        arguments.put("password", password.getText().toString());
        arguments.put("name", name.getText().toString());
        JSONObject argumentsBody = new JSONObject(arguments);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                routeSignUp,
                argumentsBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (statusResponse == 201)
                        openView(response);
                    else
                    {
                        Toast.makeText(SignUp.this, response.getString("mensaje"),
                                Toast.LENGTH_SHORT).show();
                        signUp.setEnabled(true);
                    }
                }
                catch (JSONException e)
                {
                    System.out.println("Error JSON: " + e);
                    e.printStackTrace();
                    signUp.setEnabled(true);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(SignUp.this,
                        "Error en respuesta del servidor: " + error,
                        Toast.LENGTH_SHORT).show();
                signUp.setEnabled(true);
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

    public void openView(JSONObject response)
    {
        JSONObject struct = response;
        try
        {
            finish();   
            Intent intent = new Intent(SignUp.this, Home.class);
            DataConfig.setName(struct.getString("name"));
            DataConfig.setEmail(struct.getString("email"));
            DataConfig.setPhoneNumber(struct.getString("phoneNumber"));
            DataConfig.setPassword(struct.getString("password"));
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}