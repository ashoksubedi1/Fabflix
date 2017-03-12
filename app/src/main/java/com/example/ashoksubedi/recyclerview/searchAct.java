package com.example.ashoksubedi.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class searchAct extends AppCompatActivity {
    private EditText queryText;
    private SharedPreferences sharedPreferences;
    private static final String MY_PREF = "myPref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        queryText  = (EditText) findViewById(R.id.movieInput);

        String query = getSharedPreferences(MY_PREF, MODE_PRIVATE).getString("query", "");
        queryText.setText(query);

    }

    public void SearchFunc(View view){
        Intent intent = new Intent(this, MainActivity.class);

        String searchtext =  queryText.getText().toString();  // a query typed  as input
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
        editor.putString("query", searchtext);
        editor.apply();
        intent.putExtra("searchtext",searchtext);
        startActivity(intent);


    }
}
