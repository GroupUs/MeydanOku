package com.example.androidproje;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {


    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        username=this.getIntent().getExtras().getString("username");


        }

public void YeniOyun(View v)
{
    if(v.getId()==R.id.yenioyunbtn){
        final ImageView iv = (ImageView) findViewById(R.id.imageView);

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
    public void Skorlar(View v)
    {
        if(v.getId()==R.id.skorbtn){
            Intent intent=new Intent(getApplicationContext(), Score2.class);
            intent.putExtra("username", username);
            startActivity(intent);


        }

    }
}



