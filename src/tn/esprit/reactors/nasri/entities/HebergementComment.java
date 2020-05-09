/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author nasri
 */
public class HebergementComment {
    private int id;
    private int userId;
    private int hebergementId;
    private String content;
    private Date creationDate;

    public HebergementComment() {
    }

    public HebergementComment(int id, int userId, int hebergementId, String content, Date creationDate) {
        this.id = id;
        this.userId = userId;
        this.hebergementId = hebergementId;
        this.content = content;
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, hebergementId, content, creationDate);
    }

    @Override
    public String toString() {
        String hebergementCommentStr = "Hebergement Comment#" + id + ": \n"
                + "Content: " + content + "\n"
                + "Creation date: " + creationDate + "\n";

        return hebergementCommentStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getHebergementId() {
        return hebergementId;
    }

    public void setHebergementId(int hebergementId) {
        this.hebergementId = hebergementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
