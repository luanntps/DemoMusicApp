package com.dungps19554.gatifymusic.model;

public class NgheSi {
   private int hinh;
    private  String tenNS;

    public NgheSi(int hinh, String tenNS) {
        this.hinh = hinh;
        this.tenNS = tenNS;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTenNS() {
        return tenNS;
    }

    public void setTenNS(String tenNS) {
        this.tenNS = tenNS;
    }
}
