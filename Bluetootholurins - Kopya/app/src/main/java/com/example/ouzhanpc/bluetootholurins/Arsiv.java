package com.example.ouzhanpc.bluetootholurins;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Arsiv extends AppCompatActivity {
    SQLiteDatabase db=null;
    public ArrayList<String> myArrayList=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arsiv);
        ListView lstArsiv=(ListView)findViewById(R.id.lstArsiv);

        ArrayList<String> dbArray=myArrayListToDatabase();
        ListAdapter myListAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dbArray);
        lstArsiv.setAdapter(myListAdapter);
    }

    public ArrayList<String> myArrayListToDatabase()
    {
        db=openOrCreateDatabase("SuHesaplama",MODE_PRIVATE,null);
        String selectQuery="select * from Water";
        Cursor c=db.rawQuery(selectQuery,null);
        c.moveToFirst();
        String dbString="";


        while (!c.isAfterLast())
        {

            if(c.getString(c.getColumnIndex("watername"))!=null) {
                dbString += c.getString(c.getColumnIndex("watername"));
                dbString += "\n";


                String xkoor = c.getString(c.getColumnIndex("watername"));
                String ykoor = c.getString(c.getColumnIndex("waterdate"));

                myArrayList.add(xkoor + "    " + ykoor);
            }
         c.moveToNext();
        }
        db.close();

        return myArrayList;



    }
}
