package com.online.tournament.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.tournament.model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
}
