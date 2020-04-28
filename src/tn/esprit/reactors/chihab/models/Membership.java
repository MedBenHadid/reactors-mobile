package tn.esprit.reactors.chihab.models;

import tn.esprit.reactors.chihab.models.enums.AccessType;



public class Membership {
    private int id;
    private String fonction, description;
    private User member;

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
    private AccessType access;

    public Membership(int id, String fonction, String description) { 
        this.id=id;
        this.fonction=fonction;
        this.description=description;
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
        return "Membership{" +
                "id=" + id +
                ", fonction='" + fonction + '\'' +
                ", description='" + description + '\'' +
                ", access=" + access +
                '}';
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
