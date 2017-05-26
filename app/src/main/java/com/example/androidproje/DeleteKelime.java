package com.example.androidproje;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 22.05.2017.
 */
public class DeleteKelime {


    String rivalPlayer;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    public DeleteKelime(String rivaPlayer) {
        this.rivalPlayer=rivaPlayer;
    }


    public void Delete() {
        new Delete_Test().execute(rivalPlayer);

    }

    class Delete_Test extends AsyncTask<String, String, String> {

        String TEST_DELETE_URL;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TEST_DELETE_URL = "http://challangerace.000webhostapp.com/DeleteKelime.php";
        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success = false;
            String rivalplayer=args[0];

            List<NameValuePair> data = new ArrayList<>();

            data.add(new BasicNameValuePair("rivalplayer",rivalplayer));


            try {

                JSONObject json = jsonParser.makeHttpRequest(TEST_DELETE_URL, "POST", data);
                success = json.getBoolean("success");

            } catch (Exception e) {
                return "something wrong";
            }
            if (success == true) {
                return "silindi";
            } else return "Bir sorunla karşılaşıldı";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }

    }

}
