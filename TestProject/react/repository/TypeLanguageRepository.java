
package react.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import react.model.TypeLanguage;


@Repository
public interface TypeLanguageRepository extends JpaRepository<TypeLanguage, Integer> {
    
}
