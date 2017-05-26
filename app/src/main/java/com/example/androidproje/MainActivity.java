package com.example.androidproje;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {


    private String username;
    ImageView iv;
    Button btn_yenioyun, btn_skor, btn_cikis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=this.getIntent().getExtras().getString("username");
        iv = (ImageView) findViewById(R.id.imageView);
        btn_yenioyun=(Button)findViewById(R.id.yenioyunbtn);
        btn_skor=(Button)findViewById(R.id.skorbtn);
        btn_cikis=(Button)findViewById(R.id.btn_cikis);
        Typeface face= Typeface.createFromAsset(getAssets(), "JustBreatheBdObl7.otf");
        btn_yenioyun.setTypeface(face);
        btn_skor.setTypeface(face);
        btn_cikis.setTypeface(face);

        }
    public void CikisYap(View v){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
public void YeniOyun(View v)
{
    if(v.getId()==R.id.yenioyunbtn){


        ViewPropertyAnimator anim = iv.animate();

        final Animation an= AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an2= AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        // final Animation an3= AnimationUtils.loadAnimation(getBaseContext(),R.anim.slide_up);
        // iv.startAnimation(an);
        iv.animate().rotation(360).setDuration(700).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            // implement all the method with empty bodies, but this one is important:
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(getApplicationContext(), Kategori.class);


                intent.putExtra("username", username);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.alpha(0f).setDuration(700).start();


    }
}
    public void Skorlar(View v) {
        if (v.getId() == R.id.skorbtn) {
            final ImageView iv = (ImageView) findViewById(R.id.imageView2);

            final ViewPropertyAnimator anim = iv.animate();


            iv.animate().rotation(360).setDuration(700).setListener(new Animator.AnimatorListener() {

              @Override
              public void onAnimationStart(Animator animation) {

               }

            public void onAnimationEnd(Animator animation) {
                iv.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(), Score2.class);
                intent.putExtra("username", username);
                startActivity(intent);

                 }

                @Override
                public void onAnimationCancel (Animator animation){


                  }

                @Override
                 public void onAnimationRepeat (Animator animation){

                  }
                   }
            );

            anim.alpha(0f).setDuration(700).start();
            iv.setVisibility(View.VISIBLE);

        }


    }
}



