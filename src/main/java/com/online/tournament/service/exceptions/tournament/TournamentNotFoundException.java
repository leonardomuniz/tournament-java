package com.online.tournament.service.exceptions.tournament;

public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException() {
        super("Match not found");
    }
}
