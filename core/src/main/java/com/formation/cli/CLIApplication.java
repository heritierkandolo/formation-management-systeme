package com.formation.cli;

import com.formation.core.engine.PluginManager;
import com.formation.core.service.FormationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class CLIApplication implements CommandLineRunner {
    private final PluginManager pluginManager;
    private final FormationService formationService;

    public CLIApplication(PluginManager pluginManager, FormationService formationService) {
        this.pluginManager = pluginManager;
        this.formationService = formationService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Formation Management System CLI");
        System.out.println("Type 'help' for commands or 'exit' to quit");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("formation^> ");
            String input = scanner.nextLine();
            if ("exit".equals(input)) break;
            if ("help".equals(input)) showHelp();
        }
    }

    private void showHelp() {
        System.out.println("Available commands: help, exit");
    }
}
