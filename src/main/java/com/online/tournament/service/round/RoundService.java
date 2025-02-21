package com.online.tournament.service.round;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.round.RoundDto;
import com.online.tournament.DTO.tournament.TournamentDto;
import com.online.tournament.mapper.RoundMapper;
import com.online.tournament.mapper.TournamentMapper;
import com.online.tournament.model.Round;
import com.online.tournament.repository.RoundRepository;
import com.online.tournament.service.exceptions.round.RoundNotFoundException;
import com.online.tournament.service.tournament.TournamentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoundService {

    private final RoundRepository repository;

    private final TournamentService tournamentService;

    private final TournamentMapper tournamentMapper;

    private final RoundMapper roundMapper;

    public List<RoundDto> findAll() {
        List<Round> rounds = repository.findAll();
        return rounds.stream()
                .map(roundMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoundDto getById(UUID id) {
        Round round = repository.findById(id).orElse(null);
        return round != null ? roundMapper.toDto(round) : null;
    }

    public RoundDto create(Round input) {
        TournamentDto tournament = tournamentService.getById(input.getTournament().getId());
        input.setTournament(tournamentMapper.toEntity(tournament));

        Round round = new Round();
        round.setRound(input.getRound());
        round.setTournament(tournamentMapper.toEntity(tournament));
        round.setMatches(input.getMatches());
        repository.save(round);

        return roundMapper.toDto(round);
    }

    public RoundDto edit(Round input, UUID id) throws RoundNotFoundException {
        Round round = repository.findById(id)
                .orElseThrow(() -> new RoundNotFoundException());
        updateEntity(round, input);

        round = repository.save(round);

        return roundMapper.toDto(round);
    }

    public boolean delete(UUID id) throws RoundNotFoundException {
        repository.findById(id).orElseThrow(() -> new RoundNotFoundException());
        repository.deleteById(id);

        return true;
    }

    private void updateEntity(Round round, Round input) {
        round.setRound(input.getRound() != 0 ? input.getRound() : round.getRound());
        round.setTournament(input.getTournament() != null ? input.getTournament() : round.getTournament());
        round.setMatches(input.getMatches() != null ? input.getMatches() : round.getMatches());
    }
}
