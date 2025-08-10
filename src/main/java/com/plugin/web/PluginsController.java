package com.plugin.web;

import com.plugin.api.TrainingPlugin;
import com.plugin.loader.PluginManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PluginsController {
  private final PluginManager pluginManager;

  public PluginsController(PluginManager pluginManager) {
    this.pluginManager = pluginManager;
  }

  @GetMapping("/plugins")
  public Map<String, Object> list() {
    List<TrainingPlugin> plugins = pluginManager.loadAll();
    List<String> names = plugins.stream().map(TrainingPlugin::getName).toList();

    Map<String, Object> resp = new LinkedHashMap<>();
    resp.put("count", names.size());
    resp.put("plugins", names);
    return resp;
  }
}
