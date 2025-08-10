package com.formation.plugins.module;

import com.formation.core.api.*;
import com.formation.core.model.Formation;
import com.formation.core.model.PluginStatus;
import java.util.Collections;
import java.util.List;

public class ModuleManagementPlugin implements FormationPlugin {
    private PluginStatus status = PluginStatus.INITIALIZED;

    @Override
    public String getName() { return "Module Management Plugin"; }
    @Override
    public String getVersion() { return "1.0.0"; }
    @Override
    public String getDescription() { return "Plugin for managing modules"; }

    @Override
    public void initialize(PluginContext context) {
        this.status = PluginStatus.INITIALIZED;
    }

    @Override
    public void start() {
        this.status = PluginStatus.STARTED;
        System.out.println("📚 Module Management Plugin started");
    }

    @Override
    public void stop() {
        this.status = PluginStatus.STOPPED;
    }

    @Override
    public PluginStatus getStatus() { return status; }

    @Override
    public void onFormationCreated(Formation formation) {
        System.out.println("📚 ModulePlugin: Formation created - " + formation.getTitle());
    }
}
