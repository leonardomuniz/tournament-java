package com.online.tournament.DTO.tournament;

import java.util.List;
import java.util.UUID;

import com.online.tournament.model.Player;
import com.online.tournament.model.Round;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TournamentDto {
    public String name;
    public int roundNumber;
    public List<Round> rounds;
    public List<Player> players;
    public List<UUID> matches;
}
