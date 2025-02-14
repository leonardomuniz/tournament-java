package com.online.tournament.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.tournament.model.Round;

public interface RoundRepository extends JpaRepository<Round, UUID> {
}
