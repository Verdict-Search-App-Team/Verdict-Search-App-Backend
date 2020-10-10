package com.szalay.opencourtwebapp.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "hu_dontesek")
public class DecisionDto implements Serializable {

    @Id
    //@GeneratedValue
    @Column(name = "ugyszam")
    public String ugyszam;

    @Column(name = "birosag")
    public String birosag;

    @Column(name = "ugytipus")
    public String ugytipus;

    @Column(name = "hatarozatszoveg")
    public String hatarozatszoveg;

    @Column(name = "hatarozat_datuma")
    public Date hatarozatdatuma;

    @Column(name = "eljaras_eve")
    public String eljaraseve;

    @Column(name = "targy")
    public String targy;

    @Column(name = "kulcsszavak_nyelvtani")
    public String kulcsszavaknyelvtani;

    @Column(name = "kulcsszavak_kereses")
    public String kulcsszavakkereses;

    @Column(name = "megtekintesek_szama")
    public String megtekintesekszama;

    @Column(name = "kiemelt_szoveg")
    public String kiemeltszoveg;

    @Column(name = "megjegyzesek")
    public String megjegyzesek;

}
