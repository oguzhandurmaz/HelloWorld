package com.example.ouzhanpc.bluetootholurins;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SQLiteDatabase db=null;
    Button btnbaglan,btnBardak,btnArsiv;
    float x,y;
    TextView xtext,ytext;
    int sayac=0;
    ToggleButton svt;
    String a,b,son;




    private static final int ACTIVE_BLUETOOTH=1;
    private static final int ACTIVE_BAGLANTI=2;

    BluetoothAdapter myBluetoothAdapter=null;
    BluetoothDevice myDevice=null;
    BluetoothSocket mySocket=null;

    boolean baglan=false;

    private static String MAC=null;


    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //DATABASE
        db=openOrCreateDatabase("SuHesaplama",MODE_PRIVATE,null);
        String createQuery="Create table if not exists Water(_id integer primary key autoincrement, watername text, waterdate text);";
        db.execSQL(createQuery);

        //SENSÖR
        SensorManager sensoryoneticisi=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor ivmesensoru=sensoryoneticisi.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        xtext=(TextView)findViewById(R.id.xtext);
        ytext=(TextView)findViewById(R.id.ytext);
        btnbaglan=(Button)findViewById(R.id.btnbaglan);

        btnArsiv=(Button)findViewById(R.id.btnArsiv);
        svt=(ToggleButton) findViewById(R.id.SVT);

        svt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                }else{

                }
            }
        });




        if(!sensoryoneticisi.registerListener(this,ivmesensoru,SensorManager.SENSOR_DELAY_GAME))
        {
            Toast.makeText(getApplicationContext(),"Sensör Çalışmıyor",Toast.LENGTH_LONG);
        }



        //BLUETOOTH
        myBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter==null)
        {
            Toast.makeText(getApplicationContext(),"Bluetooth Cihazı yok",Toast.LENGTH_LONG).show();

        }else if(!myBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTIVE_BLUETOOTH);

        }
        btnbaglan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baglan)
                {
                    try {
                        mySocket.close();
                        baglan=false;
                        btnbaglan.setText("BAĞLAN");
                        Toast.makeText(getApplicationContext(), "BLUETOOTH BAĞLANTI YOK.", Toast.LENGTH_LONG).show();
                    }catch (IOException e){
                        Toast.makeText(getApplicationContext(),"HATA OLUŞTU.",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Intent AcListe=new Intent(MainActivity.this,List.class);
                    startActivityForResult(AcListe,ACTIVE_BAGLANTI);

                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ACTIVE_BLUETOOTH:
                if(resultCode== Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(),"Bluetooth AKTİF.!!!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bluetooth PASİF.!!!",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case ACTIVE_BAGLANTI:
                if(resultCode==Activity.RESULT_OK){
                    MAC=data.getExtras().getString(List.MacAdres);
                    //Toast.makeText(getApplicationContext(),"FİNAL MAC"+MAC,Toast.LENGTH_LONG).show();

                    myDevice=myBluetoothAdapter.getRemoteDevice(MAC);

                    try {
                          mySocket=myDevice.createRfcommSocketToServiceRecord(myUUID);

                          mySocket.connect();

                        baglan=true;

                        btnbaglan.setText("BAĞLANTIYI KES");

                        Toast.makeText(getApplicationContext(),"BAĞLANTI KURULDU."+MAC,Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                          //e.printStackTrace();

                        baglan=false;
                        Toast.makeText(getApplicationContext(),"BAĞLANIRKEN HATA!!!"+e,Toast.LENGTH_LONG).show();
                    }

                }else
                {
                    Toast.makeText(getApplicationContext(),"Bağlantı Yapılamadı!!!",Toast.LENGTH_LONG).show();
                }

        }
    }


    //VERİ TABANI DEĞERİ EKLEME
    public void AddWater(String a,String b)
    {
        db=openOrCreateDatabase("SuHesaplama",MODE_PRIVATE,null);





        String insertQuery="insert into Water(watername, waterdate) values('";
        insertQuery += a + "','" + b + "')";
        db.execSQL(insertQuery);
    }
    //DENEME BUTONU
    public void BardakOnClick(View view)
    {



        //AddWater(a,b);
        //Toast.makeText(this,"1 Kayıt Eklendi",Toast.LENGTH_SHORT).show();

    }



    public void ArsivOnClick(View view){
        Intent Arsivac=new Intent(this,Arsiv.class);
        startActivity(Arsivac);
    }

    public void KayitSil(View view)
    {
        db=openOrCreateDatabase("SuHesaplama",MODE_PRIVATE,null);
        String deleteQuery="delete from Water";
        db.execSQL(deleteQuery);
    }





    //İVME SENSÖRÜ

    @Override
    public void onSensorChanged(SensorEvent event) {
        x= (event.values[0])*10;
        y=(event.values[1])*10;
        a=String.valueOf((int)(x));
        b=String.valueOf((int)(y));
        son=a+"*"+b+"@"+"n";
        xtext.setText(a);
        ytext.setText(b);




        if(svt.isChecked())
        {

                AddWater(a,b);

        }





        if(mySocket!=null)
        {
            try {

                mySocket.getOutputStream().write(son.getBytes());
            } catch (IOException e){
                Toast.makeText(getApplicationContext(),"Veri Gönderirken Hata",Toast.LENGTH_LONG);
            }



        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
