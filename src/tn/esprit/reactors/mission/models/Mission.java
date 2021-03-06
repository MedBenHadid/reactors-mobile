package tn.esprit.reactors.mission.models;

import tn.esprit.reactors.malek.models.User;

import tn.esprit.reactors.chihab.models.Category;

import java.util.ArrayList;
import java.util.List;


public class Mission {

    private int id;
    private int domaine;
    private String TitleMission,picture,description,location;
    private int ups;
    private double lat,lon;
    private Double objectif,sumCollected;
    private User CretedBy;
    private String DateCreation,DateFin;
    private List<Invitation> invites;

    public Mission(int id, String Titre) {
        this.id = id;
        this.TitleMission = Titre;
    }
    
    public List<Invitation> getInvites() {
        return invites;
    }

    public void setInvites(List<Invitation> invites) {
        this.invites = invites;
    }

    public Mission() {
        this.invites= new ArrayList<>();
    }
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomaine() {
        return domaine;
    }

    public void setDomaine(int domaine) {
        this.domaine = domaine;
    }

    public String getTitleMission() {
        return TitleMission;
    }

    public void setTitleMission(String titleMission) {
        TitleMission = titleMission;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Double getObjectif() {
        return objectif;
    }

    public void setObjectif(Double objectif) {
        this.objectif = objectif;
    }

    public Double getSumCollected() {
        return sumCollected;
    }

    public void setSumCollected(Double sumCollected) {
        this.sumCollected = sumCollected;
    }

    public String getCretedBy() {
        return CretedBy.getUsername();
    }

    public void setCretedBy(User cretedBy) {
        CretedBy = cretedBy;
    }

    public String getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(String dateCreation) {
//        System.out.println(dateCreation);
        DateCreation = dateCreation;
    }

    public String getDateFin() {
        return DateFin;
    }

    public void setDateFin(String dateFin) {
        DateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", domaine=" + domaine +
                ", TitleMission='" + TitleMission + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", ups=" + ups +
                ", lat=" + lat +
                ", lon=" + lon +
                ", objectif=" + objectif +
                ", sumCollected=" + sumCollected +
                ", CretedBy=" + CretedBy +
                ", DateCreation=" + DateCreation +
                ", DateFin=" + DateFin +
                '}';
    }
}
 