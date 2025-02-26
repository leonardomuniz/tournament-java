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

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Round> rounds = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tournamentPlayer", joinColumns = @JoinColumn(name = "tournamentId"), inverseJoinColumns = @JoinColumn(name = "playerId"))
    private List<Player> players;

    @ManyToMany
    @JoinTable(name = "tournamentMatch", joinColumns = @JoinColumn(name = "tournamentId"), inverseJoinColumns = @JoinColumn(name = "matchId"))
    private List<Match> matches;

}
