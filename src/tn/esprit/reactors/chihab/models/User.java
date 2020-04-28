package tn.esprit.reactors.chihab.models;


import java.util.ArrayList;
import java.util.Objects;
import tn.esprit.reactors.chihab.models.enums.RoleEnum;

public class User{
    private int id;
    private String username, email, password;
    private boolean enabled, approuved, banned;
    private ArrayList<RoleEnum> roles;
    private Profile profile;

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

    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {this.profile = profile;}

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
    
    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.banned = false;
        this.enabled = true;
        this.roles = new ArrayList<RoleEnum>();
        this.roles.add(RoleEnum.ROLE_CLIENT);
        this.profile = new Profile();
    }
    
    public User(int id, String email, String username){
        this.id=id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.banned = false;
        this.enabled = true;
        this.roles = new ArrayList<RoleEnum>();
        this.roles.add(RoleEnum.ROLE_CLIENT);
        this.profile = new Profile();
    }

}
