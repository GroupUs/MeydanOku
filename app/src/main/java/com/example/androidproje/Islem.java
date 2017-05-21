package com.example.androidproje;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by CASPER on 9.05.2017.
 */
public class Islem extends Activity implements AdapterView.OnItemClickListener{

    final int MULTIPLE_CHOICE_COUNT = 5;
    private static final String ISLEMTEST_URL = "http://challangerace.000webhostapp.com/islem3.php";


    TextView question, point, time,finish;
    ListView listItems;
    LinearLayout finish_test_layout;
    Button skor;

    private Animation mBounceAnimation;

    String JSON_STRING;

    public static HashMap<String, String> formList;
    private ArrayAdapter<String> adapter;

    ArrayList<String> items;
    ArrayList<String> definitions;
    ArrayList<String> array;


    private int count=0;
    private float totalPoint=0;
    private String currentWord = "";
    private int score=0;

    String getUsername="";
    String getRivalPlayer;
    private HashMap<String ,String>Test;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.islem_test_layout);
        point = (TextView)findViewById(R.id.Point);
        question = (TextView) findViewById(R.id.tv_question);
        time=(TextView)findViewById(R.id.time);

        listItems = (ListView) findViewById(R.id.list_item);
        listItems.setAdapter(adapter);
        listItems.setOnItemClickListener(this);

        formList = new HashMap<>();
        Test= new HashMap<>();


        getUsername=this.getIntent().getExtras().getString("username");
        setQuestions();



        definitions = new ArrayList<>();
        array = new ArrayList<>(formList.keySet());
        items=new ArrayList<>(array);


        //definitions = new ArrayList<>();

        Test();




    }

    public void Test(){
        generateRandom();
        CountDownTimer countDownTimer=new CountDownTimer(10*1000,1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(""+(int)(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                time.setVisibility(View.INVISIBLE);

                if(getRivalPlayer==getUsername) {
                    SaveTest saveTest = new SaveTest(Test, getUsername);
                    saveTest.Kaydet();
                }else{
                    DeleteTest deleteTest= new DeleteTest((getRivalPlayer));
                    deleteTest.Delete();
                }
                finish_test_layout =(LinearLayout) findViewById(R.id.finish_test_layout);
                setContentView(R.layout.finish_test_layout);

                mBounceAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_animation);
                finish=(TextView)findViewById(R.id.finish);
                skor=(Button)findViewById(R.id.button_Skor);
                finish.setText("Oyun Bitti");
                finish.startAnimation(mBounceAnimation);
                skor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inte = new Intent(getApplicationContext(),Score.class);
                        inte.putExtra("rivalplayer",getRivalPlayer);
                        inte.putExtra("dogrusayisi",score);
                        startActivity(inte);
                    }
                });

             //   Intent inten= new Intent(getApplicationContext(),Score.class);
             //   inten.putExtra("score",score);



                ///veritabanına sorular eklencek

                //
              //  inten.putExtra("test",Test);
            }


        }.start();
    }


    public void setQuestions() {
        try {
            new BackgroudTask().execute().get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
//        final String json_url="http://challangerace.000webhostapp.com/islem3.php";

    class BackgroudTask extends AsyncTask<Void, Void, String> {
        /**
         * Before starting background thread Show Progress Dialog *
         */
        String json_url;
        String json_url_secondtest;
        JSONObject veritest;
        JSONObject name;
        JSONArray m_jArry;
        //  boolean failure = false;

        @Override
        protected void onPreExecute() {

            json_url = "http://challangerace.000webhostapp.com/islem3.php";
            json_url_secondtest = "http://challangerace.000webhostapp.com/islem2.php";

        }

        @Override
        protected String doInBackground(Void... args) {
            // here Check for success tag int success;
            try {

               URL url1 = new URL(json_url_secondtest);
                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                InputStream inputStram1 = httpURLConnection1.getInputStream();
                BufferedReader bufferedReader1 = new BufferedReader((new InputStreamReader(inputStram1)));
                StringBuilder stringBuilder1 = new StringBuilder();
                while ((JSON_STRING = bufferedReader1.readLine()) != null) {

                    stringBuilder1.append(JSON_STRING + "\n");

                }
                try {
                    bufferedReader1.close();
                    inputStram1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                httpURLConnection1.disconnect();
                String result1 = stringBuilder1.toString().trim();

                    JSONObject obj1 = new JSONObject(result1);
                    m_jArry = obj1.getJSONArray("result");
                    HashMap<String, String> m_li1 = new HashMap<>();

                    veritest = m_jArry.getJSONObject(m_jArry.length()-1);


                    if (veritest.getBoolean("emptytable") == false) {
                        name=m_jArry.getJSONObject(m_jArry.length()-2);
                        for (int i = 0; i < m_jArry.length()-2; i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);

                            String soruString = jo_inside.getString("soru");
                            String cevapString = jo_inside.getString("cevap");


                            formList.put(soruString, cevapString);
                        }
                        getRivalPlayer=name.getString("username");
                        return stringBuilder1.toString().trim();


                    } else {
                        try {

                            URL url = new URL(json_url);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputStram = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStram)));
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((JSON_STRING = bufferedReader.readLine()) != null) {

                                stringBuilder.append(JSON_STRING + "\n");

                            }

                            bufferedReader.close();
                            inputStram.close();
                            httpURLConnection.disconnect();
                            String result = stringBuilder.toString().trim();
                            try {
                                JSONObject obj = new JSONObject(result);
                                JSONArray m_jArry = obj.getJSONArray("result");
                                HashMap<String, String> m_li = new HashMap<>();

                                for (int i = 0; i < m_jArry.length(); i++) {
                                    JSONObject jo_inside = m_jArry.getJSONObject(i);

                                    String soruString = jo_inside.getString("soru");
                                    String cevapString = jo_inside.getString("cevap");
                                    formList.put(soruString, cevapString);
                                }
                                getRivalPlayer=getUsername;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return stringBuilder.toString().trim();


                        } catch (Exception e) {
                            return "something wrong";
                        }
                  }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
                @Override
                protected void onPostExecute (String result){
                    super.onPostExecute(result);

                }

        }
            private void generateRandom() {

        //shuffle array pick one
        Collections.shuffle(items);
        String word = items.get(0);

        question.setText(word);
        currentWord = word;

        definitions.clear();

        int number = -1;

        for (int x = 0; x < array.size(); x++) {
            if (items.get(0).equals(array.get(x))) {
                number = x;
                break;
            }
        }
        definitions.add(formList.get(word));

        Test.put(word,formList.get(word));

        for (int i = 0; i < MULTIPLE_CHOICE_COUNT - 1; i++) {
            if (number == i) {
                definitions.add(formList.get(array.get(5)));
            } else {
                definitions.add(formList.get(array.get(i)));
            }
        }
        Collections.shuffle(definitions);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_layout,
                R.id.content,
                definitions
        );
        Test.put(word,formList.get(word));
        listItems.setAdapter(adapter);
        items.remove(word);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        //Toast.makeText(this, list.getItemAtPosition(index).toString(),Toast.LENGTH_SHORT).show();
        //array.remove(index);
        //   adapter.notifyDataSetChanged();
        count++;

        if (formList.get(currentWord).equals(listItems.getItemAtPosition(index).toString())) {
            score++;
          /*  point.setTextColor(Color.GREEN);
            point.setText("+1");
            point.animate().translationX(400).withLayer();*/

            //totalPoint=totalPoint+1;

        } else {
        /*    totalPoint = (float) (totalPoint-0.5);
            point.setTextColor(Color.RED);
            point.setText("-0.5");
            point.animate().translationX(-400).withLayer();*/
        }

        generateRandom();
     /*   if(count==5) {
            items = new ArrayList<>(array);


            if (totalPoint > 0) {
                try {

                    Toast.makeText(this, "You got it!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {

                    Toast.makeText(this, "Study more!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

}