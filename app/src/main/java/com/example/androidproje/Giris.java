package com.example.androidproje;

import android.content.Intent;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by CASPER on 9.05.2017.
 */
public class Giris extends AppCompatActivity {
    EditText a;
    EditText b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_layout);

        a= (EditText) findViewById(R.id.edittext1);
        b= (EditText) findViewById(R.id.edittext2);

    }
    public void Giris1(View v) {

        if (a.getText().toString().equals("admin") && b.getText().toString().equals("1234")) {
           Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else
        {


            Log.w("IBR", "Kullanıcı adı yada şifre yanlış");
        }

    }
}
