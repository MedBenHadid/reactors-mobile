/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.issam.models;

import com.codename1.io.File;
import java.util.Date;

/**
 *
 * @author Issam
 */
public class Don {
    private int id ; 
    private String title,description,address,phone ; 
    private Double lat,lon;
    private int ups,state;
    private String creationDate ; 
    private File image;
    private int userId;
    private int domaine;
    private String imagePath; 
    public Don() {
    }

    public Don(String title, String description, String address, String phone, File image, Double lat, Double lon, int ups, int state, String creationDate ,  int userId,int domaine , String imagePath) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.ups = ups;
        this.state = state;
        this.creationDate = creationDate;
        this.userId = userId;
        this.domaine = domaine;
        this.imagePath = imagePath ; 
    }

    
    public Don(int id , String title, String description, String address, String phone, Double lat, Double lon , String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.imagePath = imagePath ; 

    }
    
    public Don(String title, String description, String address, String phone, Double lat, Double lon) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public int getDomaine() {
        return domaine;
    }

    public void setDomaine(int domaine) {
        this.domaine = domaine;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Don{" + "title=" + title + ", description=" + description + ", address=" + address + ", phone=" + phone + ", image=" + image + ", lat=" + lat + ", lon=" + lon + ", ups=" + ups + ", state=" + state + ", creationDate=" + creationDate + '}';
    }
            
}
