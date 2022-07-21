package com.example.myapplication.pemilik.masterKandang;

public class ModelKandang {

    private String nama_kandang;
    private String kapasitas;

    private String id_kandang;

    ModelKandang(String nama_kandang, String kapasitas, String id_kandang){
        this.nama_kandang = nama_kandang;
        this.kapasitas = kapasitas;
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

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

}
