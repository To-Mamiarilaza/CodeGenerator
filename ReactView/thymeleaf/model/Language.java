
package thymeleaf.model;

import thymeleaf.model.TypeLanguage;
import thymeleaf.model.Pays;
import jakarta.persistence.*;


@Entity
@Table(name = "language")
public class Language {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_language")
    Integer idLanguage;
    
    @Column(name = "language_name")
    String languageName;
    
    @Column(name = "etat")
    Integer etat;
    
    @ManyToOne
    @JoinColumn(name = "id_type_language")
    TypeLanguage typeLanguage;
    
    @ManyToOne
    @JoinColumn(name = "id_pays")
    Pays pays;
    
    
    
    /// Constructor
    public Language(){}
    
    
    /// Getter and setter
    public Integer getIdLanguage() {
        return idLanguage;
    }
    
    public void setIdLanguage(Integer idLanguage) {
        this.idLanguage = idLanguage;
    }
    
    public String getLanguageName() {
        return languageName;
    }
    
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
    
    public Integer getEtat() {
        return etat;
    }
    
    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
    public TypeLanguage getTypeLanguage() {
        return typeLanguage;
    }
    
    public void setTypeLanguage(TypeLanguage typeLanguage) {
        this.typeLanguage = typeLanguage;
    }
    
    public Pays getPays() {
        return pays;
    }
    
    public void setPays(Pays pays) {
        this.pays = pays;
    }
    
    
}
