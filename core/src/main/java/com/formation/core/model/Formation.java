package com.formation.core.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "formations")
public class Formation {

    @Id
    private String id; // généré côté service pour l'instant (UUID)

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private FormationStatus status = FormationStatus.DRAFT;

    @ElementCollection
    private List<String> participants;

    public Formation() { }

    public Formation(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = FormationStatus.DRAFT;
    }

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public FormationStatus getStatus() { return status; }
    public void setStatus(FormationStatus status) { this.status = status; }

    public List<String> getParticipants() { return participants; }
    public void setParticipants(List<String> participants) { this.participants = participants; }
}
