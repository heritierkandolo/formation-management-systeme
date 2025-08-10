package com.formation.core.api;

public interface Command {
    String getName();
    String getDescription();
    void execute(String[] args);
}
