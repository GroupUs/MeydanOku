package com.example.androidproje;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class Score extends AppCompatActivity {
    JSONParser jsonParser1 = new JSONParser();

    String score_rival="0";
    static String json = "";

    String durum=null;
    TextView userscore, rivalscore ,sonuc;
    ImageView img,img2,img1;
    String user,rivalplayer,UsersDogruSayisi;
    TextView userdurum,rivaldurum,username,rivalname;
    Button anaSayfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        userscore=(TextView)findViewById(R.id.skor1);
        rivalscore=(TextView)findViewById(R.id.Skor2);
        sonuc=(TextView)findViewById(R.id.sonucDurum);
        username=(TextView)findViewById(R.id.username);
        rivalname=(TextView)findViewById(R.id.rivalname);

        userdurum=(TextView)findViewById(R.id.seninskor);
        rivaldurum=(TextView)findViewById(R.id.onunskoru);

        img=(ImageView)findViewById(R.id.img);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);

        anaSayfa=(Button)findViewById(R.id.button_Anasayfa);

        user=getIntent().getExtras().getString("username");
        rivalplayer=getIntent().getExtras().getString("rivalplayer");
        UsersDogruSayisi=getIntent().getExtras().getString("dogrusayisi");

        Typeface face= Typeface.createFromAsset(getAssets(), "JustBreatheBdObl7.otf");

        userdurum.setTypeface(face);
        rivaldurum.setTypeface(face);
        sonuc.setTypeface(face);
        anaSayfa.setTypeface(face);

        username.setText(user);
        rivalname.setText(rivalplayer);

        Kaydet();



        int scoreUser=Integer.parseInt(UsersDogruSayisi);
        int scoreRival=Integer.parseInt(score_rival);

        userscore.setText(UsersDogruSayisi);

        if(durum ==null) {
            rivalscore.setText(score_rival);
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

public void Kaydet(){
    try {
        new SkorKaydet().execute(user,UsersDogruSayisi,rivalplayer).get();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
}


    class SkorKaydet extends AsyncTask<String, String, String> {

        String SKOR_URL;
        JSONArray m_jArry;
        JSONObject scoresonuc;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SKOR_URL = "http://challangerace.000webhostapp.com/SkorTable.php";
        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success = false;
            String username = args[0];
            String dogrusayisi = args[1];
            String rivalplayer = args[2];


            List<NameValuePair> data = new ArrayList<>();

            data.add(new BasicNameValuePair("username", username));
            data.add(new BasicNameValuePair("dogrusayisi", dogrusayisi));
            data.add(new BasicNameValuePair("rivalplayer", rivalplayer));
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

                if(m_jArry.length()>=2) {
                    scoresonuc = m_jArry.getJSONObject(m_jArry.length() - 2);
                    score_rival = scoresonuc.getString("dogrusayisi");
                }else{
                    score_rival="0";
                }
                JSONObject suce=m_jArry.getJSONObject(m_jArry.length()-1);
                success=suce.getBoolean("success");
              /*  try {
                    JSONObject json = jsonParser1.makeHttpRequest(SKOR_URL, "GET", data);
                    success = json.getBoolean("success");
                    score_rival = json.getString("dogrusayisi");
*/

                if (success == true) {
                    return "Kaydedildi";
                } else{
                    durum="Rakip bekleniyor...";
                    return durum;
                }

            } catch (Exception e) {
            return "something wrong";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }

    }
}
