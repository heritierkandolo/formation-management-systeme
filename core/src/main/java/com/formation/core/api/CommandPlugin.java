package com.formation.core.api;

import java.util.List;

public interface CommandPlugin extends Plugin {
    List<Command> getCommands();
}
