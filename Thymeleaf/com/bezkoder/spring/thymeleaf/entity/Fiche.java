
package com.bezkoder.spring.thymeleaf.entity;

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
    
    @Column(name = "etat")
    Integer etat;
    
    @Column(name = "salaire")
    Double salaire;
    
    @Column(name = "admin")
    Boolean admin;
    
    
    
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
    
    public Integer getEtat() {
        return etat;
    }
    
    public void setEtat(Integer etat) {
        this.etat = etat;
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
    
    
}
