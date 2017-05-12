package com.example.androidproje;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by CASPER on 9.05.2017.
 */
public class Login extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


    }

    public void Giriş1(View v)
    {
        if(v.getId()==R.id.girişBtn){
            Intent intent=new Intent(getApplicationContext(), Giris.class);
            startActivity(intent);

        }
    }
    public void Kayıt1(View v)
    {
        if(v.getId()==R.id.kayıtBtn){
            Intent intent=new Intent(getApplicationContext(), Kayit.class);
            startActivity(intent);

        }
    }


}
