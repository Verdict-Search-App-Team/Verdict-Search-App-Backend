package com.szalay.opencourtwebapp.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pp_felulvizsgalat")
public class DecisionDto {


    @Id
    @Column(name = "szam")
    public String ugyszam;

    @Column(name = "hatarozatszoveg")
    public String hatarozatStringClean;

    @Column(name = "bevezeto")
    public String bevezeto;

    @Column(name = "rendelkezo")
    public String rendelkezo;

    @Column(name = "tenyallas")
    public String tenyallas;

    @Column(name = "jogiindokolas")
    public String jogiindokolas;

    @Column(name = "zaro")
    public String zaro;

    @Column(name = "birosag")
    public String birosagneve;

    @Column(name = "szekhely")
    public String birosagSzekhelye;

    @Column(name = "ugytipus")
    public String ugyTipus;

    @Column(name = "eljarastipus")
    public String eljarasTipus;

    @Column(name = "eljarasszakasz")
    public String eljarasSzakasz;

    @Column(name = "targy")
    public String targy;

    @Column(name = "dontes")
    public String dontes;

    @Column(name = "dontesmasodfok")
    public String dontesmasodfok;

    @Column(name = "donteselsofok")
    public String donteselsofok;

    public String keresettSzovegResz;

    public DecisionDto() {
        this.keresettSzovegResz = null;
    }


}
