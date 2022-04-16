package com.luannt.model;

public class BaiHat {
    int hinh;
    String tenBH;

    public BaiHat(int hinh, String tenBH) {
        this.hinh = hinh;
        this.tenBH = tenBH;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }
}
