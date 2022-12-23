package com.dmt.minhhien.thuocnamapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dmt.minhhien.thuocnamapp.Database.Database;
import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    Database database;
    List<ThuocNam> allplant;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = new Database(this);
        allplant =  database.getCayThuoc();
        new LoadingData().execute();
    }
    private class LoadingData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                @Override
                public void run() {
                    // Sau khi hoàn thành sẽ đóng activity này và chạy main activity
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }, 500);
        }
    }
}
