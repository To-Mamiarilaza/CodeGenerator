package test;

import java.sql.Date;
import java.sql.Time;

public class Fiche {
    /// Field
    Integer idFiche;
    String name;
    String firstname;
    String address;
    String mail;
    String description;
    String photo;
    Integer etat;
    Date birthDate;
    Time startTime;
    
    
    /// Constructor
    public Fiche(){}
    
    
    /// Getter and setter
    public Integer getIdFiche() {
        return idFiche;
    }
    
    public void setIdFiche(Integer idFiche) {
        this.idFiche = idFiche;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMail() {
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public Integer getEtat() {
        return etat;
    }
    
    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    
}
