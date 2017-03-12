package com.example.ashoksubedi.recyclerview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signin extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String MY_PREF = "myPref";
    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.pass);
        String email = getSharedPreferences(MY_PREF, MODE_PRIVATE).getString("email", "");

        emailEditText.setText(email);


    }
    public void connectToTomcat(View view){

        //

        final Map<String, String> params = new HashMap<>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;

        final String email = emailEditText.getText().toString();
        final String pw = passwordEditText.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.apply();

        final ProgressDialog progressDialog =  new ProgressDialog(this);
        if(email.isEmpty() || pw.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), " Please fill up !", Toast.LENGTH_LONG).show();
        }

        params.put("email", email);
        params.put("password", pw);


        String url = "http://ec2-54-202-112-215.us-west-2.compute.amazonaws.com:8080/WebProject/AndroidLogIn/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);

                        if(response.contains("1")){
                            Intent resultPage = new Intent(signin.this, signin.class);
                            resultPage.putExtra("result", response);
                            startActivity(resultPage);
                            Toast.makeText(getApplicationContext(), "Invalid User or Passwords. Please try again", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent resultPage = new Intent(signin.this, searchAct.class);
                            startActivity(resultPage);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                params.put("email", email);
                params.put("password", pw);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);



    }


}



