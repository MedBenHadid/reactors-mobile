package tn.esprit.reactors.chihab.models;

import java.util.Objects;

public class Association {

    private int id;
    private String nom,photoAgence,pieceJustificatif,rue,description,ville,horaireTravail;
    private int manager,domaine,telephone,codePostal;
    private boolean approuved;
    private Double lat,lon;
    public Association() {
    }

    public Association(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Association(int manager, int domaine, String nom, String photoAgence, String pieceJustificatif, String rue, String description, String ville, String horaireTravail, int telephone, int codePostal, boolean approuved, Double lat, Double lon) {
        this.manager = manager;
        this.domaine = domaine;
        this.nom = nom;
        this.photoAgence = photoAgence;
        this.pieceJustificatif = pieceJustificatif;
        this.rue = rue;
        this.description = description;
        this.ville = ville;
        this.horaireTravail = horaireTravail;
        this.telephone = telephone;
        this.codePostal = codePostal;
        this.approuved = approuved;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.nom);
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
        final Association other = (Association) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }



    

    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public int getDomaine() {
        return domaine;
    }

    public void setDomaine(int domaine) {
        this.domaine = domaine;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhotoAgence() {
        return photoAgence;
    }

    public void setPhotoAgence(String photoAgence) {
        this.photoAgence = photoAgence;
    }

    public String getPieceJustificatif() {
        return pieceJustificatif;
    }

    public void setPieceJustificatif(String pieceJustificatif) {
        this.pieceJustificatif = pieceJustificatif;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getHoraireTravail() {
        return horaireTravail;
    }

    public void setHoraireTravail(String horaireTravail) {
        this.horaireTravail = horaireTravail;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public boolean isApprouved() {
        return approuved;
    }

    public void setApprouved(boolean approuved) {
        this.approuved = approuved;
    }


    @Override
    public String toString() {
        return " {" + "id=" + id + "} ";
    }

}
