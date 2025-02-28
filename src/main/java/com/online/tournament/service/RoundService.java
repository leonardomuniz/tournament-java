package com.online.tournament.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.RoundDto;
import com.online.tournament.model.Match;
import com.online.tournament.model.Round;
import com.online.tournament.repository.RoundRepository;
import com.online.tournament.service.exceptions.round.RoundNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoundService {

    private final RoundRepository repository;

    public List<RoundDto> findAll() {
        List<Round> rounds = repository.findAll();
        return rounds.stream().map(this::toDto).collect(Collectors.toList());
    }

    public RoundDto getById(UUID id) throws RoundNotFoundException {
        Round round = repository.findById(id).orElseThrow(() -> new RoundNotFoundException("Round not found"));
        return toDto(round);
    }

    public RoundDto create(Round input) {
        Round round = new Round();
        updateEntity(round, input);
        round = repository.save(round);
        return toDto(round);
    }

    public RoundDto edit(Round input, UUID id) throws RoundNotFoundException {
        Round round = repository.findById(id).orElseThrow(() -> new RoundNotFoundException("Round not found"));
        updateEntity(round, input);
        round = repository.save(round);
        return toDto(round);
    }

    public boolean delete(UUID id) throws RoundNotFoundException {
        repository.findById(id).orElseThrow(() -> new RoundNotFoundException("Round not found"));
        repository.deleteById(id);
        return true;
    }

    private void updateEntity(Round round, Round input) {
        round.setRound(input.getRound() != 0 ? input.getRound() : round.getRound());
        round.setTournament(input.getTournament() != null ? input.getTournament() : round.getTournament());
        round.setMatches(input.getMatches() != null ? input.getMatches() : round.getMatches());
    }

    private RoundDto toDto(Round round) {
        return RoundDto.builder()
                .id(round.getId())
                .finished(round.getFinished())
                .round(round.getRound())
                .tournamentId(round.getTournament().getId())
                .matches(round.getMatches().stream().map(Match::getId).collect(Collectors.toList()))
                .build();
    }
}
