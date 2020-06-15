package tn.esprit.reactors.malek.models;


import java.util.ArrayList;
import java.util.Objects;
import tn.esprit.reactors.chihab.models.enums.RoleEnum;

public class User{
    private int id;
    private String username, email, password;
    private boolean enabled, approuved, banned;
    private ArrayList<RoleEnum> roles;
    private String nom,prenom,adresse,image,cin;
    private int telephone;

    public boolean isApprouved() {
        return approuved;
    }

    public void setApprouved(boolean approuved) {
        this.approuved = approuved;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public ArrayList<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<RoleEnum> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }
    @Override
    public int hashCode() {return Objects.hash(getId());}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public void setApprouved(Boolean approuved) {this.approuved = approuved;}
    public boolean getEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public boolean isAdmin() {return this.roles.contains(RoleEnum.ROLE_SUPER_ADMIN);}
    public boolean isAssociationAdmin() {return this.roles.contains(RoleEnum.ROLE_ADMIN_ASSOCIATION);}
    public boolean isMember() { return this.roles.contains(RoleEnum.ROLE_MEMBER); }
    public boolean isClient() { return this.roles.contains(RoleEnum.ROLE_CLIENT); }
    public boolean getApprouved() {return approuved;}
    public void addRole(RoleEnum role) {
        this.roles.add(role);
    }
    public void removeRole(RoleEnum role) {this.roles.remove(role);}
    
    
    
    public User() {
    }
    
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.banned = false;
        this.enabled = true;
        this.roles = new ArrayList<>();
        this.roles.add(RoleEnum.ROLE_CLIENT);
    }
    
    public User(int id, String email, String username){
        this.id=id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.banned = false;
        this.enabled = true;
        this.roles = new ArrayList<>();
        this.roles.add(RoleEnum.ROLE_CLIENT);
    }
    
    public User(String username,String image){
        this.username=username;
        this.image=image;
    }
    
    
    public User(int id, String email,String password, String username,String nom,String prenom,int telephone,String adresse,String image,String cin){
        this.id=id;
        this.email = email;
        this.username = username;
        this.password = password;
          this.nom= nom;
          this.prenom= prenom;
          this.telephone= telephone;
          this.adresse= adresse;
          this.image=image ;
          this.cin= cin;
        this.banned = false;
        this.enabled = true;
        this.roles = new ArrayList<RoleEnum>();
        this.roles.add(RoleEnum.ROLE_CLIENT);
    }
}
