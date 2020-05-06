package tn.esprit.reactors.chihab.models;

import tn.esprit.reactors.chihab.models.enums.AccessType;



public class Membership {
    public static final String INVITE_PENDING = "INVITE_PENDING";
    public static final String REQUEST_PENDING = "REQUEST_PENDING";
    public static final String DENIED_BY_USER = "DENIED_BY_USER";
    public static final String DENIED_BY_ASS = "DENIED_BY_ASS";
    public static final String ACCEPTED = "ACCEPTED";

    private int id, assId, memberId,role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    private String fonction, description, status;
    private double lon,lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public int getAssId() {
        return assId;
    }

    public void setAssId(int assId) {
        this.assId = assId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private AccessType access;

    public Membership(int id, int assId, int memberId, int role,String fonction, String description, String status, double lon, double lat) { 
        this.id=id;
        this.fonction=fonction;
        this.description=description;
        this.status=status;
        this.assId=assId;
        this.memberId=memberId;
        this.lon=lon;
        this.lat=lat;
        this.role=role;
    }

    public Membership(int assId, int memberId, int role,String fonction, String description, String status, double lon, double lat) {
        this.assId = assId;
        this.memberId = memberId;
        this.fonction = fonction;
        this.description = description;
        this.status = status;
        this.role=role;
        this.lon=lon;
        this.lat=lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public AccessType getAccess() { return access; }

    public void setAccess(int access) {
        if (access == 3)
            this.access = AccessType.DELIVER;
        else if (access == 2)
            this.access = AccessType.READ;
        else if (access == 1)
            this.access = AccessType.WRITE;
    }

    @Override
    public String toString() {
        return "Membership{" + "id=" + id + ", assId=" + assId + ", memberId=" + memberId + ", status=" + status + '}';
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Membership other = (Membership) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
