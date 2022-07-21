package com.example.myapplication.pemilik.masterkaryawan;

public class ModelClassMasterKaryawan {

    private String nama;
    private String jabatan;
    private String notelp;
    private String email;
    private String status;

    ModelClassMasterKaryawan(String nama_jabatan, String nama, String no_telp, String email, String status){
        this.nama = nama;
        this.jabatan = nama_jabatan;
        this.notelp = no_telp;
        this.email = email;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

}
