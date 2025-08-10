package com.formation.core.service;

import com.formation.core.model.Formation;
import com.formation.core.model.FormationStatus;
import com.formation.core.repository.FormationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormationService {

    private final FormationRepository repository;

    public FormationService(FormationRepository repository) {
        this.repository = repository;
    }

    /** Nombre total de formations (utile pour l'accueil). */
    public long count() {
        return repository.count();
    }

    /** Création ou mise à jour d'une formation. */
    public Formation createFormation(Formation formation) {
        if (formation.getId() == null || formation.getId().isBlank()) {
            formation.setId(UUID.randomUUID().toString());
        }
        if (formation.getStatus() == null) {
            formation.setStatus(FormationStatus.DRAFT);
        }
        return repository.save(formation);
    }

    public List<Formation> getAllFormations() {
        return repository.findAll();
    }

    public Formation getFormationById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    /** Marque une formation comme terminée. Retourne true si l'ID existe. */
    public boolean complete(String id) {
        return repository.findById(id).map(f -> {
            f.setStatus(FormationStatus.COMPLETED);
            repository.save(f);
            return true;
        }).orElse(false);
    }
}
