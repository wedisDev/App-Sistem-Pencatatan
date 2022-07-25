package com.example.ternakin.pemilik.masterTernak;

public class ModelClassMasterTernak {

    private String tanggal_datang;
    private String jumlah_bibit;

    private String id_periode;
    private String nama_kandang;
    private String id_kandang;

    public ModelClassMasterTernak(String tanggal_datang, String jumlah_bibit, String id_periode, String nama_kandang, String id_kandang){
        this.tanggal_datang = tanggal_datang;
        this.jumlah_bibit = jumlah_bibit;
        this.id_periode = id_periode;
        this.nama_kandang = nama_kandang;
        this.id_kandang = id_kandang;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getNama_kandang() {
        return nama_kandang;
    }

    public void setNama_kandang(String nama_kandang) {
        this.nama_kandang = nama_kandang;
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
