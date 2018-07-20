package com.example.iervan.crudbaru;

public class konfigurasi {

    public static final String URL_ADD="http://192.168.1.102:8080/api/v1/pegawai/create";
    public static final String URL_GET_ALL = "http://192.168.1.102:8080/api/v1/pegawai";
    public static final String URL_GET_EMP = "http://192.168.1.102:8080/api/v1/pegawai/edit/";
    public static final String URL_UPDATE_EMP = "http://192.168.1.102:8080/api/v1/pegawai/update";
    public static final String URL_DELETE_EMP = "http://192.168.1.102:8080/api/v1/pegawai/destroy/";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "desg"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_GAJIH = "salary"; //salary itu variabel untuk gajih

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJIH = "salary";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";

}
