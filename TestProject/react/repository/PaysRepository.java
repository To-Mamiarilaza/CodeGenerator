
package react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.model.Pays;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer> {
    
}
