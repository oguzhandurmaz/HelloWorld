package com.example.ouzhanpc.udemy_listview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private Button btnSayfa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSayfa= (Button) findViewById(R.id.btnSecond);

        listView= (ListView) findViewById(R.id.txtListview);

        List<String> isim=new ArrayList<>();
        isim.add("Oğuzhan");
        isim.add("Durmaz");

        final ArrayList<String> list=ArrayCrreate();
        list.addAll(isim);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list.get(position),Toast.LENGTH_SHORT).show();

            }
        });
    }
    public ArrayList<String> ArrayCrreate(){
        ArrayList<String> list=new ArrayList<>();
        return list;
    }

    public void btnClick(View view){
        Intent intent = new Intent(MainActivity.this,SecondActiviy.class);
        intent.putExtra(getString(R.string.name),"Oğuzhan");
        ArrayList<String> list=new ArrayList<>();
        list.add("Durmaz Array");
        Bundle bundle=new Bundle();

        bundle.putParcelable("Array", (Parcelable) list);
        intent.putExtra("bundle",bundle);

        startActivity(intent);
    }


}
