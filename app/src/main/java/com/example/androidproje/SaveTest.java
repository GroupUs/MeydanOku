package com.example.androidproje;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Asus on 18.05.2017.
 */
public class SaveTest extends Islem {

    HashMap<String, String> test;
    String username;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    public SaveTest(HashMap test, String username) {
        this.test = test;
        this.username=username;
    }


    public void Kaydet() {

        ArrayList<String> array = new ArrayList<>();
        array = new ArrayList<>(test.keySet());


        for (int i = 0; i <array.size(); i++) {

            String sorup = array.get(i);
            String cevapp = (String) test.get(sorup);
            new TestKaydet().execute(sorup,cevapp,username);
        }
    }

    private final String TEST_URL = "http://challangerace.000webhostapp.com/Test.php";


    class TestKaydet extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success = false;
            String sorupp = args[0];
            String cevappp= args[1];
            String username = args[2];

            List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("soru", sorupp));
                params.add(new BasicNameValuePair("cevap", cevappp));
                params.add(new BasicNameValuePair("testnumber",username));
                params.add(new BasicNameValuePair("cozuldumu","no"));
            
                try {
                    JSONObject json = jsonParser.makeHttpRequest(TEST_URL, "POST", params);
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
               Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


        }

    }

}