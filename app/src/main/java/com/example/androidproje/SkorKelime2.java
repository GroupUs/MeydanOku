package com.example.androidproje;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SkorKelime2 extends AppCompatActivity {


    TextView userscore, rivalscore ,sonuc;

    static String json = "";

    String score_rivald="0";
    String score_rivalname;
    String score_userd="0";
    String user;
    String durum;
    TextView userdurum,rivaldurum,username,rivalname;
    ImageView img,img2,img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        userdurum=(TextView)findViewById(R.id.seninskor);
        rivaldurum=(TextView)findViewById(R.id.onunskoru);

        userscore=(TextView)findViewById(R.id.skor1);
        rivalscore=(TextView)findViewById(R.id.Skor2);
        sonuc=(TextView)findViewById(R.id.sonucDurum);

        username=(TextView)findViewById(R.id.username);
        rivalname=(TextView)findViewById(R.id.rivalname);

        img=(ImageView)findViewById(R.id.img);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);


        Typeface face= Typeface.createFromAsset(getAssets(), "JustBreatheBdObl7.otf");

        userdurum.setTypeface(face);
        rivaldurum.setTypeface(face);
        sonuc.setTypeface(face);

        user=getIntent().getExtras().getString("username");
        getScore();

        username.setText(user);
        rivalname.setText(score_rivalname);


        int scoreUser=Integer.parseInt(score_userd);
        int scoreRival=Integer.parseInt(score_rivald);
        userscore.setText(score_userd);

        if(durum ==null) {
            rivalscore.setText(score_rivald);
            if (scoreUser >scoreRival) {
                sonuc.setText("Kazandın!!");
                img.setImageResource(R.drawable.image6);
            } else  if(scoreUser==scoreRival) {
                img1.setImageResource(R.drawable.image1);
                img2.setImageResource(R.drawable.image2);
                sonuc.setText("Berabere");
            }else{
                img.setImageResource(R.drawable.sadimage1);
                sonuc.setText("Kaybettin :(");
            }
        }else{
            sonuc.setText(durum.toString());
        }
    }

    public void Anasayfa(View v){
        Intent in = new Intent(this,MainActivity.class);
        in.putExtra("username",user);
        startActivity(in);
    }
    public void getScore(){
        try {
            new getSkor().execute(user).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    class getSkor extends AsyncTask<String, String, String> {

        String SKOR_URL;
        JSONArray m_jArry;
        JSONObject Rivalname,DogruSUser,DogruSRival, SuccessD;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SKOR_URL = "http://challangerace.000webhostapp.com/SkorKelime2.php";
        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success = false;
            String username = args[0];



            List<NameValuePair> data = new ArrayList<>();

            data.add(new BasicNameValuePair("username", username));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(SKOR_URL);
                httppost.setEntity(new UrlEncodedFormEntity(data));

                HttpResponse resp = httpclient.execute(httppost);
                InputStream is = resp.getEntity().getContent();

                BufferedReader myReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                sb.append(myReader.readLine() + "\n");
                String line = "";
                while ((line = myReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                json = sb.toString().trim();

                JSONObject myjson = new JSONObject(json);
                m_jArry =myjson.getJSONArray("result");

                if(m_jArry.length()>=4) {

                    DogruSUser = m_jArry.getJSONObject(0);
                    Rivalname = m_jArry.getJSONObject(1);
                    DogruSRival = m_jArry.getJSONObject(2);
                    SuccessD = m_jArry.getJSONObject(3);


                    score_userd=DogruSUser.getString("dogrusayisiuser");
                    score_rivald = DogruSRival.getString("dogrusayisi");
                    score_rivalname=Rivalname.getString("username");
                    success=SuccessD.getBoolean("success");
                }else{
                    score_rivald="0";
                }
                //JSONObject suce=m_jArry.getJSONObject(m_jArry.length()-1);
                // success=suce.getBoolean("success");
              /*  try {
                    JSONObject json = jsonParser1.makeHttpRequest(SKOR_URL, "GET", data);
                    success = json.getBoolean("success");
                    score_rival = json.getString("dogrusayisi");
*/

                if (success == true) {
                    return "kaydedildi";
                } else{
                    durum="Rakip bekleniyor...";
                    return durum;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }

    }
}
