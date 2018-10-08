/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventsmanagement.entities;

import java.sql.Date;

/**
 *
 * @author Amine CHAKER
 */
public class Events {
    
    private int id;
    private String subject;
    private String nom;
    private Date date;
    private String lieux;
    private int nbrPlace;

    public Events() {
    }

    
    public Events(String subject, String nom, Date date, String lieux, int nbrPlace) {
        this.subject = subject;
        this.nom = nom;
        this.date = date;
        this.lieux = lieux;
        this.nbrPlace = nbrPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

    public String getLieux() {
        return lieux;
    }

    public void setLieux(String lieux) {
        this.lieux = lieux;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    @Override
    public String toString() {
        return "Events{" + "id=" + id + ", subject=" + subject + ", nom=" + nom + ", date=" + date + ", lieux=" + lieux + ", nbrPlace=" + nbrPlace + '}';
    }
    
    
}
