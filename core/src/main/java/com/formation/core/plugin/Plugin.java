package com.formation.core.plugin;

public interface Plugin {
    String getName();
    String getVersion();
    String getIcon();
    PluginStatus getStatus();

    void start() throws Exception;
    void stop()  throws Exception;
}
