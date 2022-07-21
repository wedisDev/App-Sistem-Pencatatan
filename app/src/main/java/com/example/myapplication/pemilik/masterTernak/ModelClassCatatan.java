package com.example.myapplication.pemilik.masterTernak;

public class ModelClassCatatan {
    String pakan_harian;
    String berat_badan;
    String tanggal_catatan;
    String jumlah_mati;
    String jumlah_kaling;
    String kode_pakan;
    String sisa_ayam;
    String umur_ayam;
    String jumlah_pakan;

    public ModelClassCatatan(String pakan_harian, String berat_badan, String tanggal_catatan, String jumlah_mati, String jumlah_kaling, String kode_pakan, String sisa_ayam, String umur_ayam, String jumlah_pakan) {
        this.pakan_harian = pakan_harian;
        this.berat_badan = berat_badan;
        this.tanggal_catatan = tanggal_catatan;
        this.jumlah_mati = jumlah_mati;
        this.jumlah_kaling = jumlah_kaling;
        this.kode_pakan = kode_pakan;
        this.sisa_ayam = sisa_ayam;
        this.umur_ayam = umur_ayam;
        this.jumlah_pakan = jumlah_pakan;
    }

    public String getSisa_ayam() {
        return sisa_ayam;
    }

    public void setSisa_ayam(String sisa_ayam) {
        this.sisa_ayam = sisa_ayam;
    }

    public String getUmur_ayam() {
        return umur_ayam;
    }

    public void setUmur_ayam(String umur_ayam) {
        this.umur_ayam = umur_ayam;
    }

    public String getJumlah_pakan() {
        return jumlah_pakan;
    }

    public void setJumlah_pakan(String jumlah_pakan) {
        this.jumlah_pakan = jumlah_pakan;
    }

    public String getPakan_harian() {
        return pakan_harian;
    }

    public void setPakan_harian(String pakan_harian) {
        this.pakan_harian = pakan_harian;
    }

    public String getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(String berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getTanggal_catatan() {
        return tanggal_catatan;
    }

    public void setTanggal_catatan(String tanggal_catatan) {
        this.tanggal_catatan = tanggal_catatan;
    }

    public String getJumlah_mati() {
        return jumlah_mati;
    }

    public void setJumlah_mati(String jumlah_mati) {
        this.jumlah_mati = jumlah_mati;
    }

    public String getJumlah_kaling() {
        return jumlah_kaling;
    }

    public void setJumlah_kaling(String jumlah_kaling) {
        this.jumlah_kaling = jumlah_kaling;
    }

    public String getKode_pakan() {
        return kode_pakan;
    }

    public void setKode_pakan(String kode_pakan) {
        this.kode_pakan = kode_pakan;
    }
}
