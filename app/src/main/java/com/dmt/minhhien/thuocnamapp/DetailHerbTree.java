/*
package com.dmt.minhhien.thuocnamapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;

import java.util.ArrayList;
import java.util.List;

public class DetailHerbTree extends AppCompatActivity {
    private TextView tenCay, tenGoiKhac, tenKhoaHoc, tenHo, boPhanDUng, chuTri, lieuDung, luuY;
    List<ThuocNam> suDung = new ArrayList<ThuocNam>();
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herb_tree);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ab = getSupportActionBar();
        ab.setTitle("Thông tin chi tiết");

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        ThuocNam thuocnam = (ThuocNam) bundle.get("object_user");


        tenCay = findViewById(R.id.detail_info_tenCay);
        tenGoiKhac  = findViewById(R.id.detail_info_tenGoiKhac);
        tenKhoaHoc = findViewById(R.id.detail_info_tenKhoaHoc);
        tenHo = findViewById(R.id.detail_info_tenHo);
        boPhanDUng = findViewById(R.id.detail_info_boPhanDung);
        chuTri = findViewById(R.id.detail_info_chuTri);
        lieuDung = findViewById(R.id.detail_info_lieuDung);
        luuY= findViewById(R.id.detail_info_luuY);

        tenCay.setText(thuocnam.getTenCay());
        tenGoiKhac.setText(thuocnam.getTenGoiKhac());
        tenKhoaHoc.setText(thuocnam.getTenKhoaHoc());
        tenHo.setText(thuocnam.getTenHo());
        boPhanDUng.setText(thuocnam.getBoPhanDung());
        chuTri.setText(thuocnam.getChuTri());
        lieuDung.setText(thuocnam.getLieuLuong());
        luuY.setText(thuocnam.getLuuY());

        ImageView imageView = (ImageView) findViewById(R.id.myimage);
        imageView.setImageResource(R.drawable.xichdongnam);

    }
}

*/
package com.dmt.minhhien.thuocnamapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmt.minhhien.thuocnamapp.Adapter.SearchAdapter;
import com.dmt.minhhien.thuocnamapp.Database.Database;
import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;
import com.ortiz.touchview.TouchImageView;

public class DetailHerbTree extends AppCompatActivity {
    private TextView tenCay, tenGoiKhac, tenKhoaHoc, tenHo, boPhanDUng, chuTri, lieuDung, luuY;
    private ImageView hinhAnh;

    TextView txtMoRong;
    TextView txtNotFoundHo, txtNotFoundNhom;
    RecyclerView recyclerViewCungHo, recyclerViewCungNhom;
    RecyclerView.LayoutManager layoutManager2, layoutManager3;
    SearchAdapter adapter;
    LinearLayout lncungho, lncungnhom;
    ImageView imgExpandHo, imgExpandNhom;
    Database database;
    int idcay, idho, idnhom;
    String tenHoExpand, tenNhomExpand;
    TextView tenHo2, tenNhom2;
    Bitmap bitmap;
    ActionBar ab;
    Dialog dialog;
    TouchImageView img;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herb_tree);
        dialog =  new Dialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        ab = getSupportActionBar();
        ab.setTitle("Thông tin chi tiết");

        ThuocNam thuocnam = (ThuocNam) bundle.get("object_user");


        tenCay = findViewById(R.id.detail_info_tenCay);
        tenGoiKhac  = findViewById(R.id.detail_info_tenGoiKhac);
        tenKhoaHoc = findViewById(R.id.detail_info_tenKhoaHoc);
        tenHo = findViewById(R.id.detail_info_tenHo);
        boPhanDUng = findViewById(R.id.detail_info_boPhanDung);
        chuTri = findViewById(R.id.detail_info_chuTri);
        lieuDung = findViewById(R.id.detail_info_lieuDung);
        luuY= findViewById(R.id.detail_info_luuY);
        hinhAnh = findViewById(R.id.myimage);

        tenCay.setText(thuocnam.getTenCay());
        tenGoiKhac.setText(thuocnam.getTenGoiKhac());
        tenKhoaHoc.setText(thuocnam.getTenKhoaHoc());
        tenHo.setText(thuocnam.getTenHo());
        boPhanDUng.setText(thuocnam.getBoPhanDung());
        chuTri.setText(thuocnam.getChuTri());
        lieuDung.setText(thuocnam.getLieuLuong());
        luuY.setText(thuocnam.getLuuY());

        byte[] data = thuocnam.getHinhAnh();
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        hinhAnh.setImageBitmap(mutableBitmap);

        hinhAnh.setClickable(true);
        hinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImgDialog();
            }
        });

        txtMoRong = findViewById(R.id.txtMoRongDetail);
        txtNotFoundHo = findViewById(R.id.txtNotFoundCungHo);
        txtNotFoundNhom = findViewById(R.id.txtNotFoundCungNhom);
        recyclerViewCungHo = findViewById(R.id.recycler_cungho);
        recyclerViewCungNhom = findViewById(R.id.recycler_cungnhom);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerViewCungHo.setLayoutManager(layoutManager2);
        recyclerViewCungHo.setHasFixedSize(true);
        layoutManager3 = new LinearLayoutManager(this);
        recyclerViewCungNhom.setLayoutManager(layoutManager3);
        recyclerViewCungNhom.setHasFixedSize(true);
        lncungho = findViewById(R.id.lnCayCungHo);
        lncungnhom = findViewById(R.id.lnCayCungNhom);
        imgExpandHo = findViewById(R.id.expandHo);
        imgExpandNhom = findViewById(R.id.expandNhom);
        tenHo2 = findViewById(R.id.txtCayCungHo);
        tenNhom2 = findViewById(R.id.txtCayCungNhom);
        database = new Database(this);
        txtNotFoundHo.setVisibility(View.GONE);
        txtNotFoundNhom.setVisibility(View.GONE);
        recyclerViewCungHo.setVisibility(View.GONE);
        recyclerViewCungNhom.setVisibility(View.GONE);

        idcay = Integer.parseInt(thuocnam.getIdCay());
        idho = Integer.parseInt(thuocnam.getIdHo());
        idnhom = Integer.parseInt(thuocnam.getIdNhom());
        getCacCayCungHo(idho, idcay);
        getCacCayCungNhom(idnhom, idcay);
        tenHoExpand = thuocnam.getTenHo();
        tenNhomExpand = thuocnam.getTenNhom();
        lncungho.setVisibility(View.VISIBLE);
        String s = String.format("Cây cùng họ: %s", tenHoExpand);
        tenHo2.setText(s);
        lncungnhom.setVisibility(View.VISIBLE);
        String sn = String.format("Cây cùng nhóm: %s", tenNhomExpand);
        tenNhom2.setText(sn);
    }
    public void openImgDialog(){
        dialog.setContentView(R.layout.dialog_img);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        img = dialog.findViewById(R.id.imgdialg);
        img.setImageBitmap(bitmap);

        TextView btnclose = (TextView) dialog.findViewById(R.id.btnCloseImgdialg);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getCacCayCungHo(Integer ma_ho, Integer ma_cay){
        adapter = new SearchAdapter(this, database.getCayThuocCungHo(ma_ho, ma_cay));
        recyclerViewCungHo.setAdapter(adapter);
    }
    public void getCacCayCungNhom(Integer ma_nhom, Integer ma_cay){
        adapter = new SearchAdapter(this, database.getCayThuocCungNhom(ma_nhom, ma_cay));
        recyclerViewCungNhom.setAdapter(adapter);
    }
    public void onClickCungHo(View view){
        if(recyclerViewCungHo.getVisibility() == View.GONE) {
            recyclerViewCungHo.setVisibility(View.VISIBLE);
            if(recyclerViewCungHo.getAdapter().getItemCount() == 0){
                txtNotFoundHo.setVisibility(View.VISIBLE);
            }
            imgExpandHo.setImageResource(R.drawable.ic_baseline_expand_less_24);
        }
        else {
            recyclerViewCungHo.setVisibility(View.GONE);
            txtNotFoundHo.setVisibility(View.GONE);
            imgExpandHo.setImageResource(R.drawable.ic_baseline_expand_more_24);
        }
    }
    public void onClickCungNhom(View view){
        if(recyclerViewCungNhom.getVisibility() == View.GONE) {
            recyclerViewCungNhom.setVisibility(View.VISIBLE);
            if(recyclerViewCungNhom.getAdapter().getItemCount() == 0){
                txtNotFoundNhom.setVisibility(View.VISIBLE);
            }
            imgExpandNhom.setImageResource(R.drawable.ic_baseline_expand_less_24);
        }
        else {
            recyclerViewCungNhom.setVisibility(View.GONE);
            txtNotFoundNhom.setVisibility(View.GONE);
            imgExpandNhom.setImageResource(R.drawable.ic_baseline_expand_more_24);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(adapter != null)
            adapter.release();
    }
}