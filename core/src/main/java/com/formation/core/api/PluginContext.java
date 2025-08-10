package com.formation.core.api;

import com.formation.core.service.FormationService;
import com.formation.core.service.UserService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginContext {
    private final Map<String, Object> properties = new ConcurrentHashMap<>();
    private FormationService formationService;
    private UserService userService;

    public PluginContext(FormationService formationService, UserService userService) {
        this.formationService = formationService;
        this.userService = userService;
    }

    public Map<String, Object> getProperties() { return properties; }
    public FormationService getFormationService() { return formationService; }
    public UserService getUserService() { return userService; }
}
