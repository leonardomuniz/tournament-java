package com.online.tournament.service.tournament;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Tournament;
import com.online.tournament.repository.TounamentRepository;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
import com.online.tournament.service.exceptions.tournament.TournamentNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TournamentService {

    private final TounamentRepository repository;

    public List<Tournament> findAll() {
        return repository.findAll();
    }

    public Tournament getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new TournamentNotFoundException());
    }

    public Tournament create(Tournament input) {
        return repository.save(input);
    }

    public Tournament edit(Tournament input, UUID id) {
        Tournament tournament = repository.findById(id).orElseThrow(() -> new TournamentNotFoundException());

        tournament.setName(input.getName() != null ? input.getName() : tournament.getName());
        tournament.setRoundNumber(
                input.getRoundNumber() != 0 ? input.getRoundNumber() : tournament.getRoundNumber());
        tournament.setRounds(input.getRounds() != null ? input.getRounds() : tournament.getRounds());
        tournament
                .setPlayers(input.getPlayers() != null ? input.getPlayers() : tournament.getPlayers());
        tournament
                .setMatches(input.getMatches() != null ? input.getMatches() : tournament.getMatches());

        return repository.save(tournament);
    }

    public boolean delete(UUID id) {
        Optional<Tournament> tournament = Optional.ofNullable(repository.findById(id).get());

        if (tournament.isEmpty()) {
            throw new MatchNotFoundException();
        }

        repository.deleteById(id);

        return true;
    }

}
