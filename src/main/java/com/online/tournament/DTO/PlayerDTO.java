package com.online.tournament.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class PlayerDTO {
    private UUID id;
    private String name;
    private String email;
    private int ranking;
}
