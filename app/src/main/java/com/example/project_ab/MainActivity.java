package com.example.project_ab;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2;
    String user_id, password, stat;
    TextView tv;
    int id;
    SharedPreferences trents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.user_id);
        et2 = findViewById(R.id.pass);
        tv = findViewById(R.id.t1);
        trents = getSharedPreferences("users", Context.MODE_PRIVATE);
    }
    class RequestTask extends AsyncTask<int[], Integer, Void> {

        public Login getData() {
            String sid = et1.getText().toString();
            String set_server_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersPass&action=getByField&field=id&value="+sid;
            BufferedReader reader = null;

            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                Log.i("asynctest", "blz");
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }
                Login reg;
                Gson gson = new Gson();
                String temp = buf.toString();
                Log.i("asynctest", "iii");
                reg = gson.fromJson(buf.toString(), Login.class);
                return reg;
            }
            catch (IOException e) {
                return new Login();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value : values[0]) {
                Login reg = getData();
                id = reg.id;
                user_id = reg.user_id;
                password = reg.password;
                stat = reg.status;
                trents.edit().putInt("trent", id).apply();
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(stat.equals("net")){
                tv.setText("This user has not been added yet");
            }
            else {
                if (et2.getText().toString().equals(password)) {
                    tv.setText("Success");
                } else {
                    tv.setText("nea");
                }
            }
                //Intent i = new Intent(MainActivity.this, GameCLass.class);
                //i.putExtra("name", String.valueOf(nick));
                //i.putExtra("token", token);
                //startActivity(i);
        }

    }

    public void onClick(View v) throws IOException {
        RequestTask async = new RequestTask();
        async.execute(new int[]{1});
    }
}
