package com.formation.plugins.quiz;

import com.formation.core.api.*;
import com.formation.core.model.Formation;
import com.formation.core.model.PluginStatus;

public class QuizPlugin implements FormationPlugin {
    private PluginStatus status = PluginStatus.INITIALIZED;

    @Override
    public String getName() { return "Quiz Plugin"; }
    @Override
    public String getVersion() { return "1.0.0"; }
    @Override
    public String getDescription() { return "Plugin for managing quizzes"; }

    @Override
    public void initialize(PluginContext context) {
        this.status = PluginStatus.INITIALIZED;
    }

    @Override
    public void start() {
        this.status = PluginStatus.STARTED;
        System.out.println("🎯 Quiz Plugin started");
    }

    @Override
    public void stop() { this.status = PluginStatus.STOPPED; }

    @Override
    public PluginStatus getStatus() { return status; }

    @Override
    public void onFormationCompleted(Formation formation) {
        System.out.println("🎯 QuizPlugin: Formation completed - " + formation.getTitle());
    }
}
