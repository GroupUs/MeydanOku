package com.example.androidproje;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CASPER on 9.05.2017.
 */
public class Kayit extends Activity {
    private EditText user, pass;
    private Button bLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    InputStream is;
    String line=null;
    String result=null;
    Boolean response;
    private static final String REGISTER_URL = "http://challangerace.000webhostapp.com/Register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayitol_layout);
        user = (android.widget.EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        bLogin = (Button)findViewById(R.id.giris);

    }

    public void KayıtOl(View v) {

        switch (v.getId()) {
            case R.id.giris:

                String Username = user.getText().toString();
                String Password = pass.getText().toString();
                new AttemptLogin().execute(Username,Password);

                // here we have used, switch case, because on login activity you may
                // also want to show registration button, so if the user is new ! we can go the
                // registration activity , other than this we could also do this without switch
                // case.
            default: break;


        }
    }


    class AttemptLogin extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Kayit.this);
            pDialog.setMessage("Attempting for register...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success;
            String username = args[0];
            String password = args[1];

            response = false;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));


            try {
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(REGISTER_URL);

                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();


            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return "Data Submit Successfully";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Data Submit Successfully", Toast.LENGTH_LONG).show();


        }
    }






        /** * Once the background process is done we need to Dismiss the progress dialog asap * **/
        protected void onPostExecute(String message) {
            Toast.makeText(getApplicationContext(), "tamam.", Toast.LENGTH_LONG).show();

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }




  /*  public void Giris1(View v) {

        if (a.getText().toString().equals("admin") && b.getText().toString().equals("1234")) {
           Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            //yorum deneme yorumu
        }
        else
        {


            Log.w("IBR", "Kullanıcı adı yada şifre yanlış");
        }

    }*/

}

