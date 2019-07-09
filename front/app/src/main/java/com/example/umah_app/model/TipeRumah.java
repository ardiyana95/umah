package com.example.umah_app.model;

public class TipeRumah {
    private Integer id;
    private String tipe;
    private Double panjang_tanah;
    private Double lebar_tanah;
    private Double panjang_bangunan;
    private Double lebar_bangunan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Double getPanjang_tanah() {
        return panjang_tanah;
    }

    public void setPanjang_tanah(Double panjang_tanah) {
        this.panjang_tanah = panjang_tanah;
    }

    public Double getLebar_tanah() {
        return lebar_tanah;
    }

    public void setLebar_tanah(Double lebar_tanah) {
        this.lebar_tanah = lebar_tanah;
    }

    public Double getPanjang_bangunan() {
        return panjang_bangunan;
    }

    public void setPanjang_bangunan(Double panjang_bangunan) {
        this.panjang_bangunan = panjang_bangunan;
    }

    public Double getLebar_bangunan() {
        return lebar_bangunan;
    }

    public void setLebar_bangunan(Double lebar_bangunan) {
        this.lebar_bangunan = lebar_bangunan;
    }
}
