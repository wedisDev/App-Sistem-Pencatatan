package com.example.myapplication;

public class DBContract {

    public static final String SERVER_LOGIN_URL ="http://192.168.18.12/Proyek%20Akhir/LoginRegistrasi/checkLogin.php";
    public static final String SERVER_REGISTER_URL ="http://192.168.18.12/Proyek%20Akhir/LoginRegistrasi/register.php";

    //master kandang
    public static final String SERVER_TAMPIL_MASTER_KANDANG ="http://192.168.18.12/Proyek%20Akhir/MasterKandang/tampilKandang.php";
    public static final String SERVER_ADD_MASTER_KANDANG = "http://192.168.18.12/Proyek%20Akhir/MasterKandang/insertKandang.php";
    public static final String SERVER_EDIT_MASTER_KANDANG = "http://192.168.18.12/Proyek%20Akhir/MasterKandang/editKandang.php";
    public static final String SERVER_DELETE_MASTER_KANDANG = "http://192.168.18.12/Proyek%20Akhir/MasterKandang/deleteKandang.php";

    //url data master karyawan
    public static final String SERVER_TAMPIL_MASTER_KARYAWAN = "http://192.168.18.12/Proyek%20Akhir/MasterKaryawan/tampilKaryawan.php";
    public static final String SERVER_ADD_MASTER_KARYAWAN ="http://192.168.18.12/Proyek%20Akhir/MasterKaryawan/insertKaryawan.php";
    public static final String SERVER_EDIT_MASTER_KARYAWAN = "http://192.168.18.12/Proyek%20Akhir/MasterKaryawan/editKaryawan.php";

    //url data master pakan
    public static final String SERVER_TAMPIL_MASTER_STOCK = "http://192.168.18.12/Proyek%20Akhir/MasterPakan/tampilPakan.php";
    public static final String SERVER_INSERT_PAKAN = "http://192.168.18.12/Proyek%20Akhir/MasterPakan/insertPakan.php";
    public static final String SPINNER_PAKAN = "http://192.168.18.12/Proyek%20Akhir/MasterPakan/spinnerPakan.php";
    public static final String SERVER_UPDATE_STOCK = "http://192.168.18.12/Proyek%20Akhir/MasterPakan/updateStock.php";

    //master periode ternak
    public static final String SERVER_TAMPIL_MASTER_TERNAK = "http://192.168.18.12/Proyek%20Akhir/MasterPeriodeTernak/tampilTernak.php";
    public static final String SERVER_INSERT_MASTER_TERNAK = "http://192.168.18.12/Proyek%20Akhir/MasterPeriodeTernak/insertTernak.php";

    //dashboard
    public static final String SERVER_COUNT_STOCK_DASHBOARD = "http://192.168.18.12/Proyek%20Akhir/Dashboard/countPakan.php";

    //statistik
    public static final String SERVER_GET_STATISTIK = "http://192.168.18.12/Proyek%20Akhir/Statistik/tampilStatistik.php";

    public static String SERVER_GET_LIST_CATATAN(String param){
        return "http://192.168.18.12/Proyek%20Akhir/CatatanHarian/getListCatatan.php?id_periode="+param;
    }
    public static final String SERVER_ADD_CATATAN ="http://192.168.18.12/Proyek%20Akhir/CatatanHarian/insertCatatan.php";

}
