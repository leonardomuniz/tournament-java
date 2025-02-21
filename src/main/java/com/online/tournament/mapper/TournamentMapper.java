package com.online.tournament.mapper;

import org.mapstruct.Mapper;

import com.online.tournament.DTO.tournament.TournamentDto;
import com.online.tournament.model.Tournament;

@Mapper(componentModel = "spring")
public interface TournamentMapper {
    Tournament toEntity(TournamentDto tournamentDto);

    TournamentDto toDto(Tournament tournament);
}
