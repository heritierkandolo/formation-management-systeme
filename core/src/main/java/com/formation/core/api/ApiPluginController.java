package com.formation.core.api;

import com.formation.core.engine.PluginManager;
import com.formation.core.plugin.Plugin;
import com.formation.core.plugin.PluginStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plugins")
public class ApiPluginController {
    private final PluginManager manager;

    public ApiPluginController(PluginManager manager) { this.manager = manager; }

    public record PluginDto(String name, String version, String icon, PluginStatus status) {
        static PluginDto of(Plugin p) { return new PluginDto(p.getName(), p.getVersion(), p.getIcon(), p.getStatus()); }
    }

    @GetMapping
    public List<PluginDto> list() {
        return manager.list().stream().map(PluginDto::of).toList();
    }

    @PostMapping("/{name}/start")
    public ResponseEntity<Void> start(@PathVariable String name) {
        return manager.start(name) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{name}/stop")
    public ResponseEntity<Void> stop(@PathVariable String name) {
        return manager.stop(name) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
