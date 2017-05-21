package com.example.androidproje;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Score extends AppCompatActivity {
    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        String user=getIntent().getExtras().getString("username");
        String rivalplayer=getIntent().getExtras().getString("rivalPlayer");
        String UsersDogruSayisi=getIntent().getExtras().getString("dogrusayisi");


        new SkorKaydet().execute(user,UsersDogruSayisi);
    }


    class SkorKaydet extends AsyncTask<String, String, String> {

        String TEST_URL;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TEST_URL = "http://challangerace.000webhostapp.com/SkorTable.php";
        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success = false;
            String username = args[0];
            String dogrusayisi=args[1];


            List<NameValuePair> data = new ArrayList<>();

            data.add(new BasicNameValuePair("username",username));
            data.add(new BasicNameValuePair("dogrusayisi", dogrusayisi));



            try {
                JSONObject json = jsonParser.makeHttpRequest(TEST_URL, "POST", data);
                success = json.getBoolean("success");

            } catch (Exception e) {
                return "something wrong";
            }
            if (success == true) {
                return "Kaydedildi";
            } else return "Bir sorunla karşılaşıldı";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }

    }
}
