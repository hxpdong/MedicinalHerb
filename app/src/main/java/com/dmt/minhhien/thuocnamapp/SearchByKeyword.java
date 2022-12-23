package com.dmt.minhhien.thuocnamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmt.minhhien.thuocnamapp.Adapter.SearchAdapter;
import com.dmt.minhhien.thuocnamapp.Database.Database;
import com.dmt.minhhien.thuocnamapp.Model.StringUtils;
import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class SearchByKeyword extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();
    List<ThuocNam> allCayThuoc;
    TextView txtresult;
    Database database;
    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_keyword);

        RadioGroup radiogroup=(RadioGroup)findViewById(R.id.radio_group);
        radiogroup.check(R.id.radio_ten);
        txtresult = (TextView) findViewById(R.id.blank);

        Intent intent =getIntent();
        String key = intent.getStringExtra("keysearch");

        //init View
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        txtresult.setText("");
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);

        //Init DB
        database = new Database(this);

        //Setup Search Bar
        materialSearchBar.setHint("Nhập từ khóa");
        materialSearchBar.setCardViewElevation(10);
        //loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //startSearch(text.toString());
                searchFilter();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //Init Adapter default set all result
        allCayThuoc = database.getCayThuoc();
        adapter = new SearchAdapter(this, allCayThuoc);
        recyclerView.setAdapter(adapter);
    }

    private void searchFilter(){
        ArrayList<ThuocNam> newList = new ArrayList<>();

        switch(mode){
            case 0:
                for(ThuocNam thuoc : allCayThuoc) {
                    String tencay = thuoc.getTenCay().toLowerCase();
                    String tengoikhac = thuoc.getTenGoiKhac().toLowerCase();
                    String tenkhoahoc = thuoc.getTenKhoaHoc().toLowerCase();
                    if(
                            tencay.contains(materialSearchBar.getText().toLowerCase()) ||
                            StringUtils.unAccent(tencay).contains(materialSearchBar.getText().toLowerCase()) ||
                            tengoikhac.contains(materialSearchBar.getText().toLowerCase()) ||
                            StringUtils.unAccent(tengoikhac).contains(materialSearchBar.getText().toLowerCase()) ||
                            tenkhoahoc.contains(materialSearchBar.getText().toLowerCase())
                    ){
                        newList.add(thuoc);
                    }
                }
                adapter.setFilter(newList);
                //recyclerView.setAdapter(adapter);
                break;
            case 1:
                for(ThuocNam thuoc : allCayThuoc) {
                    String ho = thuoc.getTenHo().toLowerCase();
                    if(ho.contains(materialSearchBar.getText().toLowerCase()) || StringUtils.unAccent(ho).contains(materialSearchBar.getText().toLowerCase())){
                        newList.add(thuoc);
                    }
                }
                adapter.setFilter(newList);
                //recyclerView.setAdapter(adapter);
                break;
            case 2:
                for(ThuocNam thuoc : allCayThuoc) {
                    String chutri = thuoc.getChuTri().toLowerCase();
                    if(chutri.contains(materialSearchBar.getText().toLowerCase()) || StringUtils.unAccent(chutri).contains(materialSearchBar.getText().toLowerCase())){
                        newList.add(thuoc);
                    }
                }
                adapter.setFilter(newList);
                //recyclerView.setAdapter(adapter);
                break;
        }
        if(recyclerView.getAdapter().getItemCount() == 0) {
            txtresult.setText("Không tìm thấy trong cơ sở dữ liệu");
        } else txtresult.setText("");
    }

    //avoid memory leak
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(adapter != null)
            adapter.release();
    }

    @SuppressLint("SuspiciousIndentation")
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_ten:
                if (checked){
                    mode = 0;
                    if(materialSearchBar.getText().isEmpty()){ break; } else
                    searchFilter();
                }
                break;
            case R.id.radio_ho:
                if (checked){
                    mode = 1;
                    if(materialSearchBar.getText().isEmpty()){ break; } else
                    searchFilter();
                }
                break;
            case R.id.radio_chutri:
                if (checked){
                    mode = 2;
                    if(materialSearchBar.getText().isEmpty()){ break; } else
                    searchFilter();
                }
                    break;
        }
    }
}