package com.online.tournament.mapper;

import org.mapstruct.Mapper;

import com.online.tournament.DTO.player.PlayerDto;
import com.online.tournament.model.Player;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    Player toEntity(PlayerDto playerDto);

    PlayerDto toDto(Player player);
}
