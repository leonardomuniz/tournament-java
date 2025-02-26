package com.online.tournament.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "match")
@Table(name = "match")
public class Match {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "result", nullable = true)
    private String result;

    @ManyToOne
    @JoinColumn(name = "roundId", nullable = true)
    @JsonBackReference
    private Round round;

    @Column(name = "finished")
    private boolean finished;

    @OneToOne
    @JoinColumn(name = "playerOneId", nullable = false)
    private Player playerOne;

    @OneToOne
    @JoinColumn(name = "playerTwoId", nullable = false)
    private Player playerTwo;

    @OneToOne
    @JoinColumn(name = "winnerId", nullable = true)
    private Player winner;
}