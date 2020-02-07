package com.szalay.OpencourtWebApp;

public class Decision {

    private String ugyszam, hatarozatStringClean, bevezeto, rendelkezo, tenyallas, jogiindokolas, zaro, birosagneve,
            birosagSzekhelye, ugyTipus, eljarasTipus, eljarasSzakasz, targy, dontes, dontesmasodfok, donteselsofok;

    public Decision(String ugyszam, String hatarozatStringClean, String bevezeto, String rendelkezo, String tenyallas,
                    String jogiindokolas,String zaro, String birosagneve, String birosagSzekhelye, String ugyTipus, String eljarasTipus,
                    String eljarasSzakasz, String targy, String dontes, String dontesmasodfok, String donteselsofok) {
        this.ugyszam = ugyszam;
        this.hatarozatStringClean = hatarozatStringClean;
        this.bevezeto = bevezeto;
        this.rendelkezo = rendelkezo;
        this.tenyallas = tenyallas;
        this.jogiindokolas = jogiindokolas;
        this.zaro = zaro;
        this.birosagneve = birosagneve;
        this.birosagSzekhelye = birosagSzekhelye;
        this.ugyTipus = ugyTipus;
        this.eljarasTipus = eljarasTipus;
        this.eljarasSzakasz = eljarasSzakasz;
        this.targy = targy;
        this.dontes = dontes;
        this.dontesmasodfok = dontesmasodfok;
        this.donteselsofok = donteselsofok;
    }

    public String getUgyszam() {
        return ugyszam;
    }

    public String getHatarozatStringClean() {
        return hatarozatStringClean;
    }

    public String getBevezeto() {
        return bevezeto;
    }

    public String getRendelkezo() {
        return rendelkezo;
    }

    public String getTenyallas() {
        return tenyallas;
    }

    public String getJogiindokolas() {
        return jogiindokolas;
    }

    public String getZaro() {
        return zaro;
    }

    public String getBirosagneve() {
        return birosagneve;
    }

    public String getBirosagSzekhelye() {
        return birosagSzekhelye;
    }

    public String getUgyTipus() {
        return ugyTipus;
    }

    public String getEljarasTipus() {
        return eljarasTipus;
    }

    public String getEljarasSzakasz() {
        return eljarasSzakasz;
    }

    public String getTargy() {
        return targy;
    }

    public String getDontes() {
        return dontes;
    }

    public String getDontesmasodfok() {
        return dontesmasodfok;
    }

    public String getDonteselsofok() {
        return donteselsofok;
    }
}
