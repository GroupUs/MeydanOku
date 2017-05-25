package com.example.androidproje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by CASPER on 9.05.2017.
 */
public class Kategori extends Activity {
    TextView oyunmodu;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategori_layout);
       oyunmodu= (TextView)findViewById(R.id.kategoritext1);
        final ImageView iv=(ImageView) findViewById(R.id.imageView5);

        final Animation an= AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        iv.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
    public void Islem1(View v)
    {
        if(v.getId()==R.id.islemBtn){
            Intent intent=new Intent(getApplicationContext(), Islem.class);
            String username=this.getIntent().getExtras().getString("username");
            intent.putExtra("username",username);
            startActivity(intent);

        }
    }
    public void Kelime1(View v)
    {
        if(v.getId()==R.id.kelimeBtn){
            Intent intent=new Intent(getApplicationContext(), Kelime.class);
            startActivity(intent);

        }
    }

}
