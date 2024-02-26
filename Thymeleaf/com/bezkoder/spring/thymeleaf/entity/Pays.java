
package com.bezkoder.spring.thymeleaf.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "pays")
public class Pays {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pays")
    Integer idPays;
    
    @Column(name = "pays_name")
    String paysName;
    
    
    
    /// Constructor
    public Pays(){}
    
    
    /// Getter and setter
    public Integer getIdPays() {
        return idPays;
    }
    
    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }
    
    public String getPaysName() {
        return paysName;
    }
    
    public void setPaysName(String paysName) {
        this.paysName = paysName;
    }
    
    
}
