
package thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymeleaf.model.Language;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    
}
