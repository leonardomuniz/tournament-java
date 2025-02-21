package com.online.tournament.DTO.player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDto {
    private String name;

    private String email;

    private int ranking;
}
