package com.online.tournament.mapper;

import org.mapstruct.Mapper;

import com.online.tournament.DTO.match.MatchDto;
import com.online.tournament.model.Match;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    Match toEntity(MatchDto matchDto);

    MatchDto toDto(Match match);
}
