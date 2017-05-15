package com.example.androidproje;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CASPER on 9.05.2017.
 */




public class Giris extends AppCompatActivity {


    private EditText user, pass;
    private Button bLogin;
    // Progress Dialog
     private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://challangerace.000webhostapp.com/Login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_layout);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        bLogin = (Button)findViewById(R.id.giris);

    }

    public void Giris1(View v) {

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
        /** * Before starting background thread Show Progress Dialog * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Giris.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); }
        @Override
        protected String doInBackground(String... args) {
            // here Check for success tag int success;

            boolean success;
            String username=args[0];
            String password=args[1];

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                Log.d("request!", "starting");

                    JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                    // checking log for json response
                    Log.d("Login attempt", json.toString());
                    // success tag for json
                    success = json.getBoolean(TAG_SUCCESS);
                    //

                if (success == true) {
                    Log.d("Successfully Login!", json.toString());
                    //       Toast.makeText(getApplication(),"success",Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    // this finish() method is used to tell android os that we are done with current
                    // activity now! Moving to other activity
                    startActivity(ii);
                    return json.getString(TAG_MESSAGE);
                }else {
                    Toast.makeText(getApplication(),"Kullanıcı adı ya da şifre yanlış",Toast.LENGTH_LONG).show();
                    return json.getString(TAG_MESSAGE);
                }
            }catch (Exception e){
             Toast.makeText(getApplication(),"Connection failed",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        /** * Once the background process is done we need to Dismiss the progress dialog asap * **/
        protected void onPostExecute(String message) {
            pDialog.dismiss();
            if (message != null){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
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
