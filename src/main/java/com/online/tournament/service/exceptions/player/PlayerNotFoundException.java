package com.online.tournament.service.exceptions.player;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException() {
        super("Player not found");
    }
}
