
package react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import react.model.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByUsername(String username);
    Utilisateur findByUsernameAndPassword(String username, String password);
}