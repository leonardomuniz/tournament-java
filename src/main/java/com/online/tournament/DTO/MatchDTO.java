package com.online.tournament.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class MatchDTO {
    private UUID playerOneId;
    private UUID playerTwoId;
    private String result;
    private UUID roundId;
}