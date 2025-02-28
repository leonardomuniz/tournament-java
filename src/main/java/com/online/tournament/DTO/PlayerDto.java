package com.online.tournament.DTO;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDto {
    private UUID id;

    private String name;

    private String email;

    private int ranking;
}
