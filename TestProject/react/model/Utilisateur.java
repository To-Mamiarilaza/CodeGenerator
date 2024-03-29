
package react.model;

import jakarta.persistence.*;


@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    Integer idUtilisateur;
    
    @Column(name = "username")
    String username;
    
    @Column(name = "password")
    String password;
    
    
    
    /// Constructor
    public Utilisateur(){}
    
    
    /// Getter and setter
    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }
    
    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
