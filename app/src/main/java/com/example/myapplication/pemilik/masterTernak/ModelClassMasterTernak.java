package com.example.myapplication.pemilik.masterTernak;

public class ModelClassMasterTernak {

    private String tanggal_datang;
    private String jumlah_bibit;

    private String id_periode;

    public ModelClassMasterTernak(String tanggal_datang, String jumlah_bibit, String id_periode){
        this.tanggal_datang = tanggal_datang;
        this.jumlah_bibit = jumlah_bibit;
        this.id_periode = id_periode;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getTanggal_datang() {
        return tanggal_datang;
    }

    public void setTanggal_datang(String tanggal_datang) {
        this.tanggal_datang = tanggal_datang;
    }

    public String getJumlah_bibit() {
        return jumlah_bibit;
    }

    public void setJumlah_bibit(String jumlah_bibit) {
        this.jumlah_bibit = jumlah_bibit;
    }

}
