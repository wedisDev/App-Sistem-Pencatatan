package com.example.myapplication.pemilik;

public class ModelClassChart {
    private String tanggal_panen;
    private int jumlah_hasil;

    public ModelClassChart(String tanggal_panen, int jumlah_hasil) {
        this.tanggal_panen = tanggal_panen;
        this.jumlah_hasil = jumlah_hasil;
    }

    public String getTanggal_panen() {
        return tanggal_panen;
    }

    public void setTanggal_panen(String tanggal_panen) {
        this.tanggal_panen = tanggal_panen;
    }

    public int getJumlah_hasil() {
        return jumlah_hasil;
    }

    public void setJumlah_hasil(int jumlah_hasil) {
        this.jumlah_hasil = jumlah_hasil;
    }
}
