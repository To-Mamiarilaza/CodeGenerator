
package react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.model.Tutorials;


@Repository
public interface TutorialsRepository extends JpaRepository<Tutorials, Integer> {
    
}
