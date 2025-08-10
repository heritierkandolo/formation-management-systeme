package com.formation.core.api;

import com.formation.core.model.Formation;
import com.formation.core.service.FormationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {
    private final FormationService service;
    public FormationController(FormationService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Formation> create(@RequestBody Formation input) {
        var saved = service.createFormation(input);
        return ResponseEntity.created(URI.create("/formation/formations/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Formation> all() { return service.getAllFormations(); }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> one(@PathVariable String id) {
        var f = service.getFormationById(id);
        return (f == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var f = service.getFormationById(id);
        if (f == null) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
