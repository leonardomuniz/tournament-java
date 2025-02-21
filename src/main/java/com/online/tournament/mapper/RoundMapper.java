package com.online.tournament.mapper;

import org.mapstruct.Mapper;

import com.online.tournament.DTO.round.RoundDto;
import com.online.tournament.model.Round;

@Mapper(componentModel = "spring")
public interface RoundMapper {
    Round toEntity(RoundDto roundDto);

    RoundDto toDto(Round round);
}
