package com.formation.core.api;

import com.formation.core.model.Formation;
import com.formation.core.service.FormationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class HomeController {

    private final FormationService service;

    public HomeController(FormationService service) {
        this.service = service;
    }

    // Page d'accueil (contexte /formation)
    @GetMapping({"/", ""})
    public String index(Model model) {
        model.addAttribute("formations", service.getAllFormations());
        model.addAttribute("formation", new Formation()); // pour le formulaire
        return "index"; // => templates/index.html
    }

    // Création via formulaire (POST)
    @PostMapping("/create")
    public String create(@ModelAttribute("formation") Formation formation) {
        if (formation.getId() == null || formation.getId().isBlank()) {
            formation.setId(UUID.randomUUID().toString());
        }
        service.createFormation(formation);
        return "redirect:/"; // recharge la page
    }

    // Suppression depuis la page
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/";
    }
}
