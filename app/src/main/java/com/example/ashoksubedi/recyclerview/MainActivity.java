package com.example.ashoksubedi.recyclerview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private static final String URL_DATA = "http://35.167.206.67:8080/FabFlix/AutocompleteServlet?search=the";


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        loadRecyclerViewData();


        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        Intent intent = getIntent();
        String title = intent.getStringExtra("searchtext");
        final String URL_DATA = "http://35.167.206.67:8080/FabFlix/AutocompleteServlet?search=" + title;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>(){


                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                       try {



                           JSONArray array = new JSONArray(response);

                           for (int i = 0; i < array.length(); i++) {
                               JSONObject o = array.getJSONObject(i);
                               ListItem item = new ListItem(
                                       o.getString("title")
                               );
                               listItems.add(item);

                           }
                           adapter = new MyAdapter(listItems, getApplicationContext());
                           recyclerView.setAdapter(adapter);
                       }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
    },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
