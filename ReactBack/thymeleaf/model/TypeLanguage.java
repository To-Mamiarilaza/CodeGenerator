
package thymeleaf.model;

import jakarta.persistence.*;


@Entity
@Table(name = "type_language")
public class TypeLanguage {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_language")
    Integer idTypeLanguage;
    
    @Column(name = "type_language_name")
    String typeLanguageName;
    
    @Column(name = "status")
    Integer status;
    
    
    
    /// Constructor
    public TypeLanguage(){}
    
    
    /// Getter and setter
    public Integer getIdTypeLanguage() {
        return idTypeLanguage;
    }
    
    public void setIdTypeLanguage(Integer idTypeLanguage) {
        this.idTypeLanguage = idTypeLanguage;
    }
    
    public String getTypeLanguageName() {
        return typeLanguageName;
    }
    
    public void setTypeLanguageName(String typeLanguageName) {
        this.typeLanguageName = typeLanguageName;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
}
