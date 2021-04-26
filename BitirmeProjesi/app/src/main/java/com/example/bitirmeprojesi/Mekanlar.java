package com.example.bitirmeprojesi;

public class Mekanlar {
    private String mekan_adi;
    private double enlem;
    private double boylam;
    private String adres;

    public Mekanlar() {
    }

    public Mekanlar(String mekan_adi, double enlem, double boylam, String adres) {
        this.mekan_adi = mekan_adi;
        this.enlem = enlem;
        this.boylam = boylam;
        this.adres = adres;
    }

    public String getMekan_adi() {
        return mekan_adi;
    }

    public void setMekan_adi(String mekan_adi) {
        this.mekan_adi = mekan_adi;
    }

    public double getEnlem() {
        return enlem;
    }

    public void setEnlem(double enlem) {
        this.enlem = enlem;
    }

    public double getBoylam() {
        return boylam;
    }

    public void setBoylam(double boylam) {
        this.boylam = boylam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
