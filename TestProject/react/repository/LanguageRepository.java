
package react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.model.Language;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    
}
