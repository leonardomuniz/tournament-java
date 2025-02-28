package com.online.tournament.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "tournament")
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "roundNumber")
    private int roundNumber;

    @Column(name = "started")
    private Boolean started;

    @Column(name = "open")
    private Boolean open;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Round> rounds = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tournament_player", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    @JsonManagedReference
    private List<Player> players;

    @ManyToMany
    @JoinTable(name = "tournament_match", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "match_id"))
    @JsonManagedReference
    private List<Match> matches;

    public void updateFrom(Tournament input) {
        if (input.getName() != null) {
            this.name = input.getName();
        }

        if (input.getRoundNumber() != 0) {
            this.roundNumber = input.getRoundNumber();
        }

        if (input.getRounds() != null && !input.getRounds().isEmpty()) {
            this.rounds = input.getRounds();
        }

        if (input.getMatches() != null && !input.getMatches().isEmpty()) {
            this.matches = input.getMatches();
        }

        if (input.getPlayers() != null && !input.getPlayers().isEmpty()) {
            this.players = input.getPlayers();
        }

        if (input.getStarted() != null) {
            this.started = input.getStarted();
        }

        if (input.getOpen() != null) {
            this.open = input.getOpen();
        }

    }
}
