/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.models;

import com.codename1.io.File;

/**
 *
 * @author Issam
 */
public class Demande {
private int id ; 
private int userId ;
private String title,description,address,rib,creationDate,phone;
private File image;
private Double lat  ;
private Double lon;

    public Demande() {
    }

    public Demande(int id, int userId, String title, String description, String address, String rib, String creationDate, File image, Double lat, Double lon , String phone) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.address = address;
        this.rib = rib;
        this.creationDate = creationDate;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.phone =  phone;
    }

     public Demande(String title, String description, String address, Double lat, Double lon , String phone,String rib) {
        
        this.title = title;
        this.description = description;
        this.address = address;
        this.rib = rib;
       this.lat = lat;
        this.lon = lon;
        this.phone =  phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description + ", address=" + address + ", rib=" + rib + ", creationDate=" + creationDate + ", image=" + image + ", lat=" + lat + ", lon=" + lon + '}';
    }


}
