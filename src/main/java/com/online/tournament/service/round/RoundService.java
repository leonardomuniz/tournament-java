package com.online.tournament.service.round;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Round;
import com.online.tournament.model.Tournament;
import com.online.tournament.repository.RoundRepository;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
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

    public List<Round> findAll() {
        return repository.findAll();
    }

    public Round getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RoundNotFoundException());
    }

    public Round create(Round input) {
        UUID tournamentId = input.getTournament().getId();
        Tournament tournament = tournamentService.getById(tournamentId);
        input.setTournament(tournament);

        if (input.getMatches() != null) {
            input.getMatches().forEach(match -> match.setRound(input));
        }

        return repository.save(input);
    }

    public Round edit(Round input, UUID id) {
        Round round = repository.findById(id)
                .orElseThrow(() -> new RoundNotFoundException());

        round.setRound(input.getRound() != 0 ? input.getRound() : round.getRound());
        round.setTournament(input.getTournament() != null ? input.getTournament() : round.getTournament());
        round.setMatches(input.getMatches() != null ? input.getMatches() : round.getMatches());

        return repository.save(round);
    }

    public boolean delete(UUID id) {
        Optional<Round> round = Optional.ofNullable(repository.findById(id).get());

        if (round.isEmpty()) {
            throw new MatchNotFoundException();
        }

        repository.deleteById(id);

        return true;
    }
}
