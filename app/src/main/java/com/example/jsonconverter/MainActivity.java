package com.example.jsonconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//WE NEED TO ADD PERMISSION IN MAINFESTS FOR INTERNET USAGE IN APP

public class MainActivity extends AppCompatActivity {
    TextView text;
    TextView resulttextview;

    public class Downloadtask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection connection= null;
            try {
                url = new URL(urls[0]);
                connection= (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);
                int data =reader.read();

                while(data != -1){
                    char current =(char)data;
                    result += current;
                    data= reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("info","not done");
                return  null;

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // THE s HERE IS SIMPLY THE FINAL RETURNED RESULT FROM THE DO IN BACKGROUND FUNCTION.
            try {
                //CRESTAED A JSON OBJECT ,getString() IS A FUNCTION OF JSONOBJECT SO WE NEED JSON OBJECT TO USE GETSTRING()
                JSONObject json = new JSONObject(s);
                //String weatherinfo = json.getString("weather");
                // CONVERTING INTO ARRAY SO THAT WE CAN AGAIN CHANGE INTO OBJECT AND USE IT AGAIN USING GETSTRING()
                //JSONArray array = new JSONArray(weatherinfo);
                String x= text.getText().toString();
                //for(int i=0;i< array.length(); i++){
                    //JSONObject weatherobj= array.getJSONObject(i);
                    Log.i("max",json.getString("max"));
                    String max=json.getString("max");
                    String min= json.getString("min");
                    String rain =json.getString("rain");
                    resulttextview.setText("Max::"+max+"\n"+"Min::"+min+"\n"+"Rain::"+rain);
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    public void clicked(View view){
        Downloadtask task= new Downloadtask();
        // sending the url through the class using execute function which is basically a function of async task
        task.execute("https://nepal-weather-api.herokuapp.com/api/?place="+text.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.textview);
        resulttextview =(TextView) findViewById(R.id.resulttextview);


    }
}