package com.luannt.model;

public class DanhSachBaiHat {
    private  int ma;
    private String tenBH;

    public DanhSachBaiHat(int ma, String tenBH) {
        this.ma = ma;
        this.tenBH = tenBH;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }
}
