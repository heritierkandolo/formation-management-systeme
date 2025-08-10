package com.formation.core.api;

import com.formation.core.model.Formation;

public interface FormationPlugin extends Plugin {
    default void onFormationCreated(Formation formation) {}
    default void onFormationUpdated(Formation formation) {}
    default void onFormationCompleted(Formation formation) {}
}
