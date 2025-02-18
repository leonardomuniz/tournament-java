package com.online.tournament.DTO.match;

import java.util.UUID;

import com.online.tournament.model.Player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutputMatchDTO {
    private UUID id;
    private String result;
    private UUID round;
    private Player playerOne;
    private Player playerTwo;
    private Player winner;
}