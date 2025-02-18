package com.online.tournament.DTO.round;

import java.util.List;
import java.util.UUID;

import com.online.tournament.DTO.match.InputMatchDTO;

import lombok.Data;

@Data
public class InputRoundDTO {
    private int round;

    private UUID tournamentId;

    private List<InputMatchDTO> matches;
}
