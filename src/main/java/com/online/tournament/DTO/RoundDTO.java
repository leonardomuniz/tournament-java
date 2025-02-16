package com.online.tournament.DTO;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RoundDTO {
    private int round;

    private UUID tournamentId;

    private List<MatchDTO> matches;
}
