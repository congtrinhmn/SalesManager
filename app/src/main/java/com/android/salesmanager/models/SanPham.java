package com.android.salesmanager.models;
//Nguyen Mau Cong Trinh
//v3
//Thoi gian hoan thanh: 4/10/18
import java.io.Serializable;

public class SanPham implements Serializable {
    private int stt;
    private int id;
    private String ma;
    private String ten;
    private String thoiGian;
    private double giaNhap;
    private double giaDeXuat;
    private double banVoiGia;
    private double sl;
    private double slNhap;
    private double slTon;
    private double von;
    private double lai;
    private double tongThu;

    //Kho
    public SanPham(int stt, int id, String ma, String ten, double slNhap, double slTon, double giaNhap, double giaDeXuat) {
        this.stt = stt;
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.slNhap = slNhap;
        this.slTon = slTon;
        this.giaDeXuat = giaDeXuat;
        this.giaNhap = giaNhap;
    }

    //Bang gia
    public SanPham(int stt, int id, String ma, String ten, double giaDeXuat) {
        this.stt = stt;
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.giaDeXuat = giaDeXuat;
    }

    //Da ban
    public SanPham(int stt, int id, String ma, String ten, double banVoiGia, double sl, String thoiGian) {
        this.stt = stt;
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.banVoiGia = banVoiGia;
        this.sl = sl;
        this.thoiGian = thoiGian;
    }

    //Thu & Chi
    public SanPham(int stt, int id, String ma, String ten, double slBanRa, double tongThu, double lai, double von, String thoiGian) {
        this.stt = stt;
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.sl = slBanRa;
        this.tongThu = tongThu;
        this.lai = lai;
        this.von = von;
        this.thoiGian = thoiGian;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaDeXuat() {
        return giaDeXuat;
    }

    public void setGiaDeXuat(double giaDeXuat) {
        this.giaDeXuat = giaDeXuat;
    }

    public double getBanVoiGia() {
        return banVoiGia;
    }

    public void setBanVoiGia(double banVoiGia) {
        this.banVoiGia = banVoiGia;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getSlNhap() {
        return slNhap;
    }

    public void setSlNhap(double slNhap) {
        this.slNhap = slNhap;
    }

    public double getSlTon() {
        return slTon;
    }

    public void setSlTon(double slTon) {
        this.slTon = slTon;
    }

    public double getVon() {
        return von;
    }

    public void setVon(double von) {
        this.von = von;
    }

    public double getLai() {
        return lai;
    }

    public void setLai(double lai) {
        this.lai = lai;
    }

    public double getTongThu() {
        return tongThu;
    }

    public void setTongThu(double tongThu) {
        this.tongThu = tongThu;
    }
}