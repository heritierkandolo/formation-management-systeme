package com.formation.core.plugin.impl;

import com.formation.core.plugin.Plugin;
import com.formation.core.plugin.PluginStatus;
import org.springframework.stereotype.Component;

@Component
public class CertificatePlugin implements Plugin {
    private PluginStatus status = PluginStatus.LOADED;

    @Override public String getName()    { return "Certificate Plugin"; }
    @Override public String getVersion() { return "1.0.0"; }
    @Override public String getIcon()    { return "fas fa-certificate"; }
    @Override public PluginStatus getStatus() { return status; }

    @Override public void start() { status = PluginStatus.STARTED; }
    @Override public void stop()  { status = PluginStatus.STOPPED; }
}
