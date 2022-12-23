package com.dmt.minhhien.thuocnamapp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<String> suggestList = new ArrayList<>();
    //Database database;

    String txtKey = "";
    Dialog dialog;
    private final int RECORD_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dialog =  new Dialog(this);
        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Cap quyen cho ung dung
        int permission_camera = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
        );
        int permission_read_storage = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if(permission_camera != PackageManager.PERMISSION_GRANTED
            || permission_read_storage != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }
    private void makeRequest(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, RECORD_REQUEST_CODE);
    }

    /** Called when the user taps the Search Keyword button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, SearchByKeyword.class);
        startActivity(intent);
    }

    public void openImgDialog(View view){
        dialog.setContentView(R.layout.dialog_mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        RadioGroup radiogroup=(RadioGroup)dialog.findViewById(R.id.radio_group_mode);

        TextView btnclose = (TextView) dialog.findViewById(R.id.btnCloseImgdialg);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @SuppressLint("SuspiciousIndentation")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pic:
                if (checked){
                    Intent intent = new Intent(this, DetectActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.radio_real:
                if (checked){
                    Intent intent = new Intent(this, DetectorActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}