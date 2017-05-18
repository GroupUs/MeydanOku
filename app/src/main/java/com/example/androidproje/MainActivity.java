package com.example.androidproje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



        }

public void Tiklandi2(View v)
{
    if(v.getId()==R.id.yenioyunbtn){
        String username=this.getIntent().getExtras().getString("username");
        Intent intent=new Intent(getApplicationContext(), Kategori.class);
        intent.putExtra("username",username);
        startActivity(intent);


    }

}


}
