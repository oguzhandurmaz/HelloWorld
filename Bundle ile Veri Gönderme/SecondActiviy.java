package com.example.ouzhanpc.udemy_listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActiviy extends AppCompatActivity {

    private static final String TAG = "SecondActiviy";

    TextView txtView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activiy);
        Log.d(TAG, "onCreate: started");

        listView= (ListView) findViewById(R.id.txtListview);


        txtView= (TextView) findViewById(R.id.txtVeri);
        try{
            Intent intent =getIntent();
            String name=intent.getStringExtra(getResources().getString(R.string.name));
            txtView.setText(name);

            Bundle bundle=intent.getBundleExtra("bundle");
            ArrayList<String> liste=bundle.getParcelable("Array");
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liste);
            listView.setAdapter(adapter);

        }catch (NullPointerException e){
            txtView.setText("Veri Alınamadı");
        }


    }
}
