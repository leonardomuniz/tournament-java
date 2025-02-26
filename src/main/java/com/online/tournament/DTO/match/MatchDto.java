package com.online.tournament.DTO.match;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDto {
    private UUID id;

    private String result;

    private boolean finished;

    private UUID roundId;

    private UUID playerOneId;

    private UUID playerTwoId;

    private UUID winnerId;
}