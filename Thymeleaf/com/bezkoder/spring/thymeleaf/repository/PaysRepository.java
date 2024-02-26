
package com.bezkoder.spring.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bezkoder.spring.thymeleaf.entity.Pays;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer> {
    
}
