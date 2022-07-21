package com.example.myapplication.pemilik.masterpakan;

public class ModelStok {
    private String nama_pakan;
    private String stok_pakan;

    ModelStok(String nama_pakan, String stok_pakan){
        this.nama_pakan = nama_pakan;
        this.stok_pakan = stok_pakan;
    }

    public String getNama_pakan() {
        return nama_pakan;
    }

    public void setNama_pakan(String nama_pakan) {
        this.nama_pakan = nama_pakan;
    }

    public String getStok_pakan() {
        return stok_pakan;
    }

    public void setStok_pakan(String stok_pakan) {
        this.stok_pakan = stok_pakan;
    }
}
