package com.szalay.opencourtwebapp;

public final class Decision {

    public final String ugyszam, hatarozatStringClean, bevezeto, rendelkezo, tenyallas, jogiindokolas, zaro, birosagneve,
            birosagSzekhelye, ugyTipus, eljarasTipus, eljarasSzakasz, targy, dontes, dontesmasodfok, donteselsofok;

    public Decision(String ugyszam, String hatarozatStringClean, String bevezeto, String rendelkezo, String tenyallas,
                    String jogiindokolas, String zaro, String birosagneve, String birosagSzekhelye, String ugyTipus,
                    String eljarasTipus,
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

}
