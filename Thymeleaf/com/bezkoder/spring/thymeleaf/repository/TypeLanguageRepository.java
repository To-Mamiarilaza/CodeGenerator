
package com.bezkoder.spring.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bezkoder.spring.thymeleaf.entity.TypeLanguage;


@Repository
public interface TypeLanguageRepository extends JpaRepository<TypeLanguage, Integer> {
    
}
