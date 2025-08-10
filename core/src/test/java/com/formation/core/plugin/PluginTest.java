package com.formation.core.plugin;

import com.formation.core.plugin.impl.ModuleManagementPlugin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PluginTest {

    @Test
    void pluginLifecycle_shouldChangeStatus() throws Exception {
        var p = new ModuleManagementPlugin();

        // Au départ
        assertEquals(PluginStatus.LOADED, p.getStatus(), "Status initial doit être LOADED");
        assertEquals("Module Management Plugin", p.getName());
        assertEquals("1.0.0", p.getVersion());
        assertNotNull(p.getIcon());

        // Start
        p.start();
        assertEquals(PluginStatus.STARTED, p.getStatus(), "Après start() le status doit être STARTED");

        // Stop
        p.stop();
        assertEquals(PluginStatus.STOPPED, p.getStatus(), "Après stop() le status doit être STOPPED");
    }
}
