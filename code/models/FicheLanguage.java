package models;

import models.Fiche;
import models.Language;
import jakarta.persistence.*;


@Entity
@Table(name = "fiche_language")
public class FicheLanguage {
    /// Field
    @ManyToOne
    @JoinColumn(name = "id_fiche")
    @Column(name = "id_fiche")
    Fiche fiche;
    
    @ManyToOne
    @JoinColumn(name = "id_language")
    @Column(name = "id_language")
    Language language;
    
    
    
    /// Constructor
    public FicheLanguage(){}
    
    
    /// Getter and setter
    public Fiche getFiche() {
        return fiche;
    }
    
    public void setFiche(Fiche fiche) {
        this.fiche = fiche;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    
}
