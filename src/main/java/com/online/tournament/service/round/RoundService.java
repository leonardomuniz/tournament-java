package com.online.tournament.service.round;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

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

    public List<Round> findAll() {
        return repository.findAll();
    }

    public Round getById(UUID id) throws RoundNotFoundException {
        return repository.findById(id).orElseThrow(() -> new RoundNotFoundException());
    }

    public Round create(Round input) {
        Round round = new Round();
        updateEntity(round, input);
        return repository.save(round);
    }

    public Round edit(Round input, UUID id) throws RoundNotFoundException {
        Round round = repository.findById(id).orElseThrow(() -> new RoundNotFoundException());
        updateEntity(round, input);
        return repository.save(round);
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
