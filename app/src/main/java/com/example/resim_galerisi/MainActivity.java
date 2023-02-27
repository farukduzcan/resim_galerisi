package com.example.resim_galerisi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1;
    TextView isimler;
    Button baslat, durdur;
    Spinner spinner1;
    EditText sure;
    SeekBar seekBar1;
    Timer zamanlayici;
    int sira =0;
    int[] resimler = {R.drawable.ab, R.drawable.bd,R.drawable.cd, R.drawable.dd, R.drawable.ed, R.drawable.fd, R.drawable.gd };
    String[] aciklama={"Taşlar ve Gökyüzü","BJK","BJK 1903","BJK KARTAL","BJK","BJK","BJK STAD"};
    String[] yon ={"ileri", "geri" };
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView1);
        isimler=findViewById(R.id.isimler);
        baslat=findViewById(R.id.baslat);
        durdur=findViewById(R.id.durdur);
        durdur.setEnabled(false);
        spinner1=findViewById(R.id.spinner1);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, yon);
        spinner1.setAdapter(adapter);

        sure=findViewById(R.id.sure);
        seekBar1=findViewById(R.id.seekBar1);
        seekBar1.setMax(resimler.length-1);
        goster();
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sira=i;
                imageView1.setImageResource(resimler[sira]);
                isimler.setText(aciklama[sira]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void ilk(View view) {
        sira=0;
        goster();

    }


    public void goster(){
        imageView1.setImageResource(resimler[sira]);
        isimler.setText(aciklama[sira]);
        seekBar1.setProgress(sira);
    }

    public void son(View view) {
        sira =resimler.length-1;
        goster();

    }


    public void ileri(View view) {
        sira++;
        if (sira==resimler.length){
            sira=0;
        }
        goster();


    }

    public void geri(View view) {
        sira--;
        if (sira==-1){
            sira= resimler.length-1;

        }
        goster();
    }


    public void basla(View view) {
        int saniye =  Integer.parseInt(sure.getText().toString());
        zamanlayici=new Timer();
        TimerTask gorev=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (spinner1.getSelectedItem().toString()=="ileri"){
                            sira++;
                            if (sira==resimler.length){
                                sira=0;
                            }
                            goster();
                        }
                        else if (spinner1.getSelectedItem().toString()=="geri"){

                            sira--;
                            if (sira==-1){
                                sira= resimler.length-1;

                            }
                            goster();
                        }

                    }
                });
            }
        };
        zamanlayici.schedule(gorev,0,saniye*1000);
        baslat.setEnabled(false);
        durdur.setEnabled(true);

    }

    public void dur(View view) {
        zamanlayici.cancel();
        durdur.setEnabled(false);
        baslat.setEnabled(true);

    }
}