package com.online.tournament.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.tournament.model.Match;

public interface MatchRepository extends JpaRepository<Match, UUID> {
}
