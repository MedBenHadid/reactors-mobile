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
public class Requete {
    private int id;
    private int user;
    private int rponse;
    private String sujet;
    private String description;
    private Date maj;
    private int statut;
    private int type;
    public Requete(int a,String d,String e,int g,int h){
    id = a;
    
    sujet = d;
    description = e;
    statut = g;
    type = h;
    }
    public Requete(String d,String e,Date f,int g,int h){
    sujet = d;
    description = e;
    maj = f;
    statut = g;
    type = h;
    }

    public Requete(String sujet, String description, int type) {
        this.sujet = sujet;
        this.description = description;
        this.type = type;
    }

    public int getid(){return id;}
    public int getuser(){return user;}
    public int getrponse(){return rponse;}
    public String getsujet(){return sujet;}
    public String getdescription(){return description;}
    public Date getmaj(){return maj;}
    public int getstatut(){return statut;}
    public int gettype(){return type;}
    public void setid(int id){this.id = id;}
    public void setuser(int id){this.user = id;}
    public void setrponse(int id){this.rponse = id;}
    public void setsujet(String id){this.sujet = id;}
    public void setdescription(String id){this.description = id;}
    public void setmaj(Date id){this.maj = id;}
    public void setstatut(int id){this.statut = id;}
    public void settype(int id){this.type = id;}
     @Override
    public String toString() {
        return " {" + "id=" + id +",sujet="+sujet+ "} ";
    }

    
    
}
