package com.formation.core.api;

import com.formation.core.model.PluginStatus;

public interface Plugin {
    String getName();
    String getVersion();
    String getDescription();
    void initialize(PluginContext context);
    void start();
    void stop();
    PluginStatus getStatus();
}
