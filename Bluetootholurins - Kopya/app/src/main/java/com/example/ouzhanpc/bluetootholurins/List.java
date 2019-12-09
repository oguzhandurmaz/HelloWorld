package com.example.ouzhanpc.bluetootholurins;


import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

public class List extends ListActivity {

    private BluetoothAdapter myBluetoothAdapter2=null;

    static String MacAdres=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        myBluetoothAdapter2=BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> EslesenCihazlar=myBluetoothAdapter2.getBondedDevices();

        if(EslesenCihazlar.size()>0)
        {
            for(BluetoothDevice cihaz : EslesenCihazlar)
            {
                String cihazadi=cihaz.getName();
                String cihazmac=cihaz.getAddress();
                ArrayBluetooth.add(cihazadi+'\n'+cihazmac);
            }
        }

        setListAdapter(ArrayBluetooth);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String genelbilgi=((TextView) v).getText().toString();
        String macadres=genelbilgi.substring(genelbilgi.length()-17);
        //Toast.makeText(getApplicationContext(),"Bilgi:"+genelbilgi,Toast.LENGTH_LONG).show();


        Intent returnmac= new Intent();
        returnmac.putExtra(MacAdres,macadres);
        setResult(RESULT_OK,returnmac);
        finish();
    }
}
