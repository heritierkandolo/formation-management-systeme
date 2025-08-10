package com.formation.core.engine;

import com.formation.core.plugin.Plugin;
import com.formation.core.plugin.PluginStatus;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PluginManager {
    private final Map<String, Plugin> registry = new ConcurrentHashMap<>();

    public PluginManager(List<Plugin> plugins) {
        for (Plugin p : plugins) registry.put(p.getName(), p);
    }

    @PostConstruct
    void startAll() {
        registry.values().forEach(p -> {
            try { p.start(); } catch (Exception ignored) {}
        });
    }

    public List<Plugin> list() { return List.copyOf(registry.values()); }

    public boolean start(String name) {
        var p = registry.get(name);
        if (p == null) return false;
        try { p.start(); return true; } catch (Exception e) { return false; }
    }

    public boolean stop(String name) {
        var p = registry.get(name);
        if (p == null) return false;
        try { p.stop(); return true; } catch (Exception e) { return false; }
    }

    public PluginStatus statusOf(String name) {
        var p = registry.get(name);
        return p != null ? p.getStatus() : null;
    }

    public List<Plugin> getAllPlugins() { return list(); }
    public void startPlugin(String name) { start(name); }
    public void stopPlugin(String name)  { stop(name); }
}
