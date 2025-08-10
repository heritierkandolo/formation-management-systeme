package com.formation.core.api;

import com.formation.core.model.Formation;
import com.formation.core.service.FormationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/formations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiFormationController {

    private final FormationService service;

    public ApiFormationController(FormationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Formation> all() {
        return service.getAllFormations();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Formation> create(@RequestBody Formation input) {
        Formation saved = service.createFormation(input);
        // L’URL de ressource inclut le context-path /formation
        URI location = URI.create("/formation/api/formations/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> one(@PathVariable String id) {
        Formation f = service.getFormationById(id);
        return (f == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (service.getFormationById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable String id) {
        return service.complete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
