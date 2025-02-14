package com.online.tournament.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.tournament.model.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    public Optional<Player> findByEmail(String email);
}
