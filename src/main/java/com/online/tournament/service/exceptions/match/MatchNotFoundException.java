package com.online.tournament.service.exceptions.match;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException() {
        super("Match not found");
    }
}
