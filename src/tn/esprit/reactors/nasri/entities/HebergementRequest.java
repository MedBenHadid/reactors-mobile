/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.entities;

import java.util.Date;
import java.util.Objects;
import tn.esprit.reactors.nasri.enums.CivilStatus;
import tn.esprit.reactors.nasri.enums.HebergementStatus;

/**
 *
 * @author nasri
 */
public class HebergementRequest {
    private int id;
    private int userId;
    private String name;
    private String description;
    private String region;
    private HebergementStatus state;
    private String nativeCountry;
    private Date arrivalDate;
    private String passportNumber;
    private CivilStatus civilStatus;
    private int childrenNumber;
    private String telephone;
    private Date creationDate;
    private boolean isAnonymous;

    public HebergementRequest() {
    }

    public HebergementRequest(int id, int userId, String description, String region,
                              HebergementStatus state, String nativeCountry,
                              Date arrivalDate, String passportNumber, CivilStatus civilStatus,
                              int childrenNumber, String name, String telephone, boolean isAnonymous) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.region = region;
        this.state = state;
        this.nativeCountry = nativeCountry;
        this.arrivalDate = arrivalDate;
        this.passportNumber = passportNumber;
        this.civilStatus = civilStatus;
        this.childrenNumber = childrenNumber;
        this.telephone = telephone;
        this.creationDate = new Date();
        this.isAnonymous = isAnonymous;
    }

    public HebergementRequest(int id, int userId, String description, String region,
                              HebergementStatus state, String nativeCountry,
                              Date arrivalDate, String passportNumber, CivilStatus civilStatus,
                              int childrenNumber, String name, String telephone, Date creationDate, boolean isAnonymous) {
        this(id, userId, description, region,
                state, nativeCountry, arrivalDate,
                passportNumber, civilStatus, childrenNumber, name, telephone, isAnonymous);
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, description, region, state, nativeCountry,
                arrivalDate, passportNumber, civilStatus, childrenNumber, telephone, creationDate, isAnonymous);
    }

    @Override
    public String toString() {
        String hebergementRequestStr = "Hebergement Request#" + id + ": \n"
                + "Name: " + name + "\n"
                + "Description: " + description + "\n"
                + "Region: " + region + "\n"
                + "State: " + (state == HebergementStatus.inProcess ? "inProcess" : "Done") + "\n"
                + "Native country: " + nativeCountry + "\n"
                + "Arrival date: " + arrivalDate + "\n"
                + "Passport number: " + passportNumber + "\n"
                + "Civil status: " + (civilStatus == CivilStatus.Married ? "Married" : "Single") + "\n"
                + "Children number: " + childrenNumber + "\n"
                + "Telephone: " + telephone + "\n"
                + "Creation date: " + creationDate + "\n"
                + "Anonymous: " + isAnonymous;

        return hebergementRequestStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public HebergementStatus getState() {
        return state;
    }

    public void setState(HebergementStatus state) {
        this.state = state;
    }

    public String getNativeCountry() {
        return nativeCountry;
    }

    public void setNativeCountry(String nativeCountry) {
        this.nativeCountry = nativeCountry;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }
}
