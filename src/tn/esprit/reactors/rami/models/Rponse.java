/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.rami.models;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Rponse {
    private int id;
    private int user_id;
    private int requete_id;
    private String Sujet,Rep;
    private Date maj;
    private int rate;

    public Rponse() {}
    
    public Rponse(int id, int user_id, int requete_id, String Sujet, String Rep, Date maj, int rate) {
        this.id = id;
        this.user_id = user_id;
        this.requete_id = requete_id;
        this.Sujet = Sujet;
        this.Rep = Rep;
        this.maj = maj;
        this.rate = rate;
    }

    public Rponse(int id, String Sujet, String Rep) {
        this.id = id;
        this.Sujet = Sujet;
        this.Rep = Rep;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRequete_id(int requete_id) {
        this.requete_id = requete_id;
    }

    public void setSujet(String Sujet) {
        this.Sujet = Sujet;
    }

    public void setRep(String Rep) {
        this.Rep = Rep;
    }

    public void setMaj(Date maj) {
        this.maj = maj;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRequete_id() {
        return requete_id;
    }

    public String getSujet() {
        return Sujet;
    }

    public String getRep() {
        return Rep;
    }

    public Date getMaj() {
        return maj;
    }

    public int getRate() {
        return rate;
    }
    
    
    
    
}
