package com.example.noti_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class SignIn extends AppCompatActivity
{
    private RequestQueue queue;
    private String routeSignIn;
    private EditText phoneNumber;
    private EditText password;
    private Button signIn;
    private int statusResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        DataConfig dataConfig = new DataConfig();
        routeSignIn = dataConfig.getUrlServer() + "/signIn";;
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        password.setText("");
        signIn = (Button)findViewById(R.id.signIn);
    }

    public void signIn(View view)
    {
        signIn.setEnabled(false);
        queue = Volley.newRequestQueue(this);
        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("phoneNumber", phoneNumber.getText().toString());
        arguments.put("password", password.getText().toString());
        JSONObject argumentsBody = new JSONObject(arguments);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                routeSignIn,
                argumentsBody, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (statusResponse == 200)
                        openView(response);
                    else
                    {
                        Toast.makeText(SignIn.this, response.getString("mensaje"),
                                Toast.LENGTH_SHORT).show();
                        signIn.setEnabled(true);
                    }
                }
                catch (JSONException e)
                {
                    System.out.println("Error JSON: " + e);
                    e.printStackTrace();
                    signIn.setEnabled(true);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(SignIn.this,
                        "Error en respuesta del servidor: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                signIn.setEnabled(true);
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

    private void openView(JSONObject response)
    {
        try
        {
            finish();
            JSONObject struct = response.getJSONObject("estructura");
            Intent intent = new Intent(SignIn.this, Home.class);
            DataConfig.setName(struct.getString("name"));
            DataConfig.setEmail(struct.getString("email"));
            DataConfig.setPhoneNumber(struct.getString("phoneNumber"));
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}