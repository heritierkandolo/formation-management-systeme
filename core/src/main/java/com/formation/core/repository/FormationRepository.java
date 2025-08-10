package com.formation.core.repository;

import com.formation.core.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, String> {
    // Méthodes custom possibles, ex.:
    // Optional<Formation> findByTitle(String title);
}
