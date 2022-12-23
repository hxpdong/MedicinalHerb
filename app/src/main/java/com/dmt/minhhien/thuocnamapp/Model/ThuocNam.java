/**
 * NCKH Project
 * ThuocNam model
 *
 */

package com.dmt.minhhien.thuocnamapp.Model;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ThuocNam implements Serializable {
    public String idCay;
    public String tenCay, tenGoiKhac, tenKhoaHoc, tenHo, boPhanDung, luuY;
    public String chuTri, lieuLuong;
    public byte[] hinhAnh;
    public String idHo;
    public String idNhom;
    public String tenNhom;

    public ThuocNam(String idCay, String tenCay, String tenGoiKhac, String tenKhoaHoc, String tenHo,
                    String boPhanDung, String luuY, String chuTri, String lieuLuong, byte[] hinhAnh, String idHo, String idNhom, String tenNhom) {
        this.idCay = idCay;
        this.tenCay = tenCay;
        this.tenGoiKhac = tenGoiKhac;
        this.tenKhoaHoc = tenKhoaHoc;
        this.tenHo = tenHo;
        this.boPhanDung = boPhanDung;
        this.luuY = luuY;
        this.lieuLuong = lieuLuong;
        this.chuTri = chuTri;
        this.hinhAnh = hinhAnh;
        this.idHo = idHo;
        this.idNhom = idNhom;
        this.tenNhom = tenNhom;
    }

    public ThuocNam(){

    }

    public String getIdCay() { return idCay; }

    public String getTenCay() {
        return tenCay;
    }

    public String getTenGoiKhac() {
        return tenGoiKhac;
    }

    public String getTenKhoaHoc() {
        return tenKhoaHoc;
    }

    public String getTenHo() { return tenHo; }

    public String getBoPhanDung() {
        return boPhanDung;
    }

    public String getLuuY() {
        return luuY;
    }

    public String getLieuLuong() {
        return lieuLuong;
    }

    public String getChuTri() {
        return chuTri;
    }

    public byte[] getHinhAnh(){
        return hinhAnh;
    }

    public String getIdHo() { return idHo; }

    public String getIdNhom() { return idNhom; }

    public String getTenNhom() { return tenNhom; }

    public void setIdCay(String idCay) {
        this.idCay = idCay;
    }

    public void setTenCay(String tenCay) {
        this.tenCay = tenCay;
    }

    public void setTenGoiKhac(String tenGoiKhac) {
        this.tenGoiKhac = tenGoiKhac;
    }

    public void setTenKhoaHoc(String tenKhoaHoc) {
        this.tenKhoaHoc = tenKhoaHoc;
    }

    public void setTenHo(String tenHo) { this.tenHo = tenHo; }

    public void setBoPhanDung(String boPhanDung) {
        this.boPhanDung = boPhanDung;
    }

    public void setLuuY(String luuY) {
        this.luuY = luuY;
    }

    public void setLieuLuong(String lieuLuong) {
        this.lieuLuong = lieuLuong;
    }

    public void setChuTri(String chuTri) {
        this.chuTri = chuTri;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setIdHo(String idHo) { this.idHo = idHo; }

    public void setIdNhom(String idNhom) { this.idNhom = idNhom; }

    public void setTenNhom(String tenNhom) { this.tenNhom = tenNhom; }
}

