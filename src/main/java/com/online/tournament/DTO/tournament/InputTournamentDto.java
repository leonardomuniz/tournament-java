package com.online.tournament.DTO.tournament;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class InputTournamentDto {
    private String name;
    private int roundNumber = 0;
    private List<UUID> rounds;
    private List<UUID> players;
    private List<UUID> matches;
}
