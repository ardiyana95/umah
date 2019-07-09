package com.example.umah_app.common;

import java.math.BigDecimal;

public class Topsis {
    private Integer id;
    private String nama;
    private BigDecimal harga;
    private String provinsi;
    private String zipcode;
    private String alamat;
    private String keterangan;
    private Integer att_kesiapan_ditempati;
    private Integer att_kota;
    private Integer att_lingkungan;
    private Integer att_akses_transportasi_publik;
    private Integer att_infrastruktur_dan_fasilitas_property;
    private Integer att_desain_dan_konstruksi;
    private Integer att_fasilitas_lingkungan_property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getAtt_kesiapan_ditempati() {
        return att_kesiapan_ditempati;
    }

    public void setAtt_kesiapan_ditempati(Integer att_kesiapan_ditempati) {
        this.att_kesiapan_ditempati = att_kesiapan_ditempati;
    }

    public Integer getAtt_kota() {
        return att_kota;
    }

    public void setAtt_kota(Integer att_kota) {
        this.att_kota = att_kota;
    }

    public Integer getAtt_lingkungan() {
        return att_lingkungan;
    }

    public void setAtt_lingkungan(Integer att_lingkungan) {
        this.att_lingkungan = att_lingkungan;
    }

    public Integer getAtt_akses_transportasi_publik() {
        return att_akses_transportasi_publik;
    }

    public void setAtt_akses_transportasi_publik(Integer att_akses_transportasi_publik) {
        this.att_akses_transportasi_publik = att_akses_transportasi_publik;
    }

    public Integer getAtt_infrastruktur_dan_fasilitas_property() {
        return att_infrastruktur_dan_fasilitas_property;
    }

    public void setAtt_infrastruktur_dan_fasilitas_property(Integer att_infrastruktur_dan_fasilitas_property) {
        this.att_infrastruktur_dan_fasilitas_property = att_infrastruktur_dan_fasilitas_property;
    }

    public Integer getAtt_desain_dan_konstruksi() {
        return att_desain_dan_konstruksi;
    }

    public void setAtt_desain_dan_konstruksi(Integer att_desain_dan_konstruksi) {
        this.att_desain_dan_konstruksi = att_desain_dan_konstruksi;
    }

    public Integer getAtt_fasilitas_lingkungan_property() {
        return att_fasilitas_lingkungan_property;
    }

    public void setAtt_fasilitas_lingkungan_property(Integer att_fasilitas_lingkungan_property) {
        this.att_fasilitas_lingkungan_property = att_fasilitas_lingkungan_property;
    }
}
