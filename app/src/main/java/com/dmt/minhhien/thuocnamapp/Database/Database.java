package com.dmt.minhhien.thuocnamapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.dmt.minhhien.thuocnamapp.Model.ThuocNam;
import com.dmt.minhhien.thuocnamapp.tflite.Classifier;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_Name = "caythuocnam6.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_Name, null, DB_VER);
    }

    //Function get all cayThuoc
    public List<ThuocNam> getCayThuoc(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //All is column name in db table
        final String MY_QUERY = "SELECT * FROM caythuoc ct JOIN ho h ON ct.ho_id=h.ho_id JOIN nhom n ON ct.nhom_id=n.nhom_id ORDER BY ten_cay ASC";

        Cursor cursor = db.rawQuery(MY_QUERY, null);
        List<ThuocNam> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                ThuocNam thuocnam = new ThuocNam();
                thuocnam.setIdCay(cursor.getString(cursor.getColumnIndexOrThrow("id_cay")));
                thuocnam.setTenCay(cursor.getString(cursor.getColumnIndexOrThrow("ten_cay")));
                thuocnam.setTenGoiKhac(cursor.getString(cursor.getColumnIndexOrThrow("ten_goikhac")));
                thuocnam.setTenKhoaHoc(cursor.getString(cursor.getColumnIndexOrThrow("ten_khoahoc")));
                thuocnam.setTenHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_ten")));
                thuocnam.setBoPhanDung(cursor.getString(cursor.getColumnIndexOrThrow("bophandung")));
                thuocnam.setLuuY(cursor.getString(cursor.getColumnIndexOrThrow("luuy")));
                thuocnam.setLieuLuong(cursor.getString(cursor.getColumnIndexOrThrow("lieu_luong")));
                thuocnam.setChuTri(cursor.getString(cursor.getColumnIndexOrThrow("chu_tri")));
                thuocnam.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow("hinhanh")));
                thuocnam.setIdHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_id")));
                thuocnam.setIdNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_id")));
                thuocnam.setTenNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_ten")));
                result.add(thuocnam);
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<ThuocNam> getCayThuocById(Integer cay_id){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        final String MY_QUERY = "SELECT * FROM caythuoc ct JOIN ho h ON ct.ho_id=h.ho_id JOIN nhom n ON ct.nhom_id=n.nhom_id WHERE id_cay = ?";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[] {"" +cay_id+ ""});

        List<ThuocNam> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                ThuocNam thuocnam = new ThuocNam();
                thuocnam.setIdCay(cursor.getString(cursor.getColumnIndexOrThrow("id_cay")));
                thuocnam.setTenCay(cursor.getString(cursor.getColumnIndexOrThrow("ten_cay")));
                thuocnam.setTenGoiKhac(cursor.getString(cursor.getColumnIndexOrThrow("ten_goikhac")));
                thuocnam.setTenKhoaHoc(cursor.getString(cursor.getColumnIndexOrThrow("ten_khoahoc")));
                thuocnam.setTenHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_ten")));
                thuocnam.setBoPhanDung(cursor.getString(cursor.getColumnIndexOrThrow("bophandung")));
                thuocnam.setLuuY(cursor.getString(cursor.getColumnIndexOrThrow("luuy")));
                thuocnam.setChuTri(cursor.getString(cursor.getColumnIndexOrThrow("chu_tri")));
                thuocnam.setLieuLuong(cursor.getString(cursor.getColumnIndexOrThrow("lieu_luong")));
                thuocnam.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow("hinhanh")));
                thuocnam.setIdHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_id")));
                thuocnam.setIdNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_id")));
                thuocnam.setTenNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_ten")));
                result.add(thuocnam );
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<ThuocNam> getCayThuocCungHo(Integer ho_id, Integer cay_id){
        SQLiteDatabase db = getReadableDatabase();

        final String MY_QUERY = "SELECT * FROM caythuoc ct JOIN ho h ON ct.ho_id=h.ho_id JOIN nhom n ON ct.nhom_id=n.nhom_id WHERE ct.ho_id = ? AND id_cay != ? ORDER BY ten_cay ASC";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[] {"" +ho_id+ "", "" +cay_id+ ""});

        List<ThuocNam> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                ThuocNam thuocnam = new ThuocNam();
                thuocnam.setIdCay(cursor.getString(cursor.getColumnIndexOrThrow("id_cay")));
                thuocnam.setTenCay(cursor.getString(cursor.getColumnIndexOrThrow("ten_cay")));
                thuocnam.setTenGoiKhac(cursor.getString(cursor.getColumnIndexOrThrow("ten_goikhac")));
                thuocnam.setTenKhoaHoc(cursor.getString(cursor.getColumnIndexOrThrow("ten_khoahoc")));
                thuocnam.setTenHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_ten")));
                thuocnam.setBoPhanDung(cursor.getString(cursor.getColumnIndexOrThrow("bophandung")));
                thuocnam.setLuuY(cursor.getString(cursor.getColumnIndexOrThrow("luuy")));
                thuocnam.setChuTri(cursor.getString(cursor.getColumnIndexOrThrow("chu_tri")));
                thuocnam.setLieuLuong(cursor.getString(cursor.getColumnIndexOrThrow("lieu_luong")));
                thuocnam.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow("hinhanh")));
                thuocnam.setIdHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_id")));
                thuocnam.setIdNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_id")));
                thuocnam.setTenNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_ten")));
                result.add(thuocnam );
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<ThuocNam> getCayThuocCungNhom(Integer nhom_id, Integer cay_id){
        SQLiteDatabase db = getReadableDatabase();

        final String MY_QUERY = "SELECT * FROM caythuoc ct JOIN ho h ON ct.ho_id=h.ho_id JOIN nhom n ON ct.nhom_id=n.nhom_id WHERE ct.nhom_id = ? AND ct.nhom_id !=99 AND id_cay != ? ORDER BY ten_cay ASC";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[] {"" +nhom_id+ "", "" +cay_id+ ""});

        List<ThuocNam> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                ThuocNam thuocnam = new ThuocNam();
                thuocnam.setIdCay(cursor.getString(cursor.getColumnIndexOrThrow("id_cay")));
                thuocnam.setTenCay(cursor.getString(cursor.getColumnIndexOrThrow("ten_cay")));
                thuocnam.setTenGoiKhac(cursor.getString(cursor.getColumnIndexOrThrow("ten_goikhac")));
                thuocnam.setTenKhoaHoc(cursor.getString(cursor.getColumnIndexOrThrow("ten_khoahoc")));
                thuocnam.setTenHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_ten")));
                thuocnam.setBoPhanDung(cursor.getString(cursor.getColumnIndexOrThrow("bophandung")));
                thuocnam.setLuuY(cursor.getString(cursor.getColumnIndexOrThrow("luuy")));
                thuocnam.setChuTri(cursor.getString(cursor.getColumnIndexOrThrow("chu_tri")));
                thuocnam.setLieuLuong(cursor.getString(cursor.getColumnIndexOrThrow("lieu_luong")));
                thuocnam.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow("hinhanh")));
                thuocnam.setIdHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_id")));
                thuocnam.setIdNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_id")));
                thuocnam.setTenNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_ten")));
                result.add(thuocnam );
            }while (cursor.moveToNext());
        }
        return result;
    }

    public List<ThuocNam> getCayThuocByIdArray(List<Integer> list_id){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        List<ThuocNam> result = new ArrayList<>();
        for (Integer cay_id : list_id) {
            final String MY_QUERY = "SELECT * FROM caythuoc ct JOIN ho h ON ct.ho_id=h.ho_id JOIN nhom n ON ct.nhom_id=n.nhom_id WHERE id_cay = ?";

            Cursor cursor = db.rawQuery(MY_QUERY, new String[] {"" +cay_id+ ""});
            if(cursor.moveToFirst()){
                do{
                    ThuocNam thuocnam = new ThuocNam();
                    thuocnam.setIdCay(cursor.getString(cursor.getColumnIndexOrThrow("id_cay")));
                    thuocnam.setTenCay(cursor.getString(cursor.getColumnIndexOrThrow("ten_cay")));
                    thuocnam.setTenGoiKhac(cursor.getString(cursor.getColumnIndexOrThrow("ten_goikhac")));
                    thuocnam.setTenKhoaHoc(cursor.getString(cursor.getColumnIndexOrThrow("ten_khoahoc")));
                    thuocnam.setTenHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_ten")));
                    thuocnam.setBoPhanDung(cursor.getString(cursor.getColumnIndexOrThrow("bophandung")));
                    thuocnam.setLuuY(cursor.getString(cursor.getColumnIndexOrThrow("luuy")));
                    thuocnam.setChuTri(cursor.getString(cursor.getColumnIndexOrThrow("chu_tri")));
                    thuocnam.setLieuLuong(cursor.getString(cursor.getColumnIndexOrThrow("lieu_luong")));
                    thuocnam.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow("hinhanh")));
                    thuocnam.setIdHo(cursor.getString(cursor.getColumnIndexOrThrow("ho_id")));
                    thuocnam.setIdNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_id")));
                    thuocnam.setTenNhom(cursor.getString(cursor.getColumnIndexOrThrow("nhom_ten")));
                    result.add(thuocnam );
                }while (cursor.moveToNext());
            }
        }
        return result;
    }
}
