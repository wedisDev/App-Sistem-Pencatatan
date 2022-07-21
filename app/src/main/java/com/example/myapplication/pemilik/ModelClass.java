package com.example.myapplication.pemilik;

public class ModelClass {

    private String nama_kandang;
    private String ket_hari;

    ModelClass(String nama_kandang, String ket_hari){
        this.nama_kandang = nama_kandang;
        this.ket_hari = ket_hari;
    }

    public String getNama_kandang() {
        return nama_kandang;
    }

    public String getKet_hari() {
        return ket_hari;
    }

    public void setNama_kandang(String nama_kandang) {
        this.nama_kandang = nama_kandang;
    }

    public void setKet_hari(String ket_hari) {
        this.ket_hari = ket_hari;
    }



}
