
package thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymeleaf.model.Fiche;


@Repository
public interface FicheRepository extends JpaRepository<Fiche, Integer> {
    
}
