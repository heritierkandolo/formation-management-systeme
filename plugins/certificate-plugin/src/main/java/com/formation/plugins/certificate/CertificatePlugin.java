package com.formation.plugins.certificate;

import com.formation.core.api.*;
import com.formation.core.model.Formation;
import com.formation.core.model.PluginStatus;

public class CertificatePlugin implements FormationPlugin {
    private PluginStatus status = PluginStatus.INITIALIZED;

    @Override
    public String getName() { return "Certificate Plugin"; }
    @Override
    public String getVersion() { return "1.0.0"; }
    @Override
    public String getDescription() { return "Plugin for managing certificates"; }

    @Override
    public void initialize(PluginContext context) {
        this.status = PluginStatus.INITIALIZED;
    }

    @Override
    public void start() {
        this.status = PluginStatus.STARTED;
        System.out.println("🏆 Certificate Plugin started");
    }

    @Override
    public void stop() { this.status = PluginStatus.STOPPED; }

    @Override
    public PluginStatus getStatus() { return status; }

    @Override
    public void onFormationCompleted(Formation formation) {
        System.out.println("🏆 CertificatePlugin: Formation completed - " + formation.getTitle());
    }
}
