package com.online.tournament.DTO.round;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundDto {
    private UUID id;

    private Boolean finished;

    private int round;

    private UUID tournamentId;

    private List<UUID> matches;
}
