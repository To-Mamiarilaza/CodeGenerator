package models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import models.Language;
import jakarta.persistence.*;


@Entity
@Table(name = "fiche")
public class Fiche {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fiche")
    Integer idFiche;
    
    @Column(name = "name")
    String name;
    
    @Column(name = "firstname")
    String firstname;
    
    @Column(name = "address")
    String address;
    
    @Column(name = "mail")
    String mail;
    
    @Column(name = "description")
    String description;
    
    @Column(name = "photo")
    String photo;
    
    @Column(name = "etat")
    Integer etat;
    
    @Column(name = "birth_date")
    Date birthDate;
    
    @Column(name = "start_time")
    Time startTime;
    
    @Column(name = "salaire")
    Double salaire;
    
    @Column(name = "admin")
    Boolean admin;
    
    @Column(name = "embauche")
    Timestamp embauche;
    
    @Column(name = "prix")
    Double prix;
    
    @ManyToOne
    @JoinColumn(name = "id_language")
    @Column(name = "id_language")
    Language language;
    
    
    
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
    
    public Double getSalaire() {
        return salaire;
    }
    
    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }
    
    public Boolean getAdmin() {
        return admin;
    }
    
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    
    public Timestamp getEmbauche() {
        return embauche;
    }
    
    public void setEmbauche(Timestamp embauche) {
        this.embauche = embauche;
    }
    
    public Double getPrix() {
        return prix;
    }
    
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    
}
