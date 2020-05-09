/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.entities;

import com.codename1.io.File;
import java.util.Date;
import java.util.Objects;
import tn.esprit.reactors.nasri.enums.HebergementStatus;

/**
 *
 * @author nasri
 */
public class HebergementOffer {
    private int id;
    private int userId;
    private String description;
    private String governorat;
    private int numberRooms;
    private int duration; // in months
    private Date creationDate;
    private HebergementStatus state;
    private String telephone;
    private File image; // file path

    public HebergementOffer() {

    }

    public HebergementOffer(int id, int userId, String description, String governorat,
                            int numberRooms, int duration,
                            HebergementStatus state, String telephone, File image) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.governorat = governorat;
        this.numberRooms = numberRooms;
        this.duration = duration;
        this.creationDate = new Date();
        this.state = state;
        this.telephone = telephone;
        this.image = image;
    }

    public HebergementOffer(int id, int userId, String description, String governorat,
                            int numberRooms, int duration, Date creationDate,
                            HebergementStatus state, String telephone, File image) {
        this(id, userId, description, governorat, numberRooms, duration, state, telephone, image);
        this.creationDate = creationDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGovernorat() {
        return governorat;
    }

    public void setGovernorat(String governorat) {
        this.governorat = governorat;
    }

    public int getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(int numberRooms) {
        this.numberRooms = numberRooms;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public HebergementStatus getState() {
        return state;
    }

    public void setState(HebergementStatus state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, description, governorat, numberRooms, duration, creationDate, state, telephone, image);
    }

    @Override
    public String toString() {
        String hebergementOfferStr = "Hebergement Offer#" + id + ": \n"
                + "Description: " + description + "\n"
                + "Governorat: " + governorat + "\n"
                + "Number of rooms: " + numberRooms + "\n"
                + "Duration: " + duration + "\n"
                + "State: " + (state == HebergementStatus.inProcess ? "inProcess" : "Done") + "\n"
                + "Telephone: " + telephone + "\n";

        return hebergementOfferStr;
    }
}
