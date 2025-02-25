package com.online.tournament.service.tournament;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Tournament;
import com.online.tournament.repository.TournamentRepository;
import com.online.tournament.service.exceptions.tournament.TournamentNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TournamentService {

    private final TournamentRepository repository;

    public List<Tournament> findAll() {
        return repository.findAll();
    }

    public Tournament getById(UUID id) {
        return repository.findById(id).orElseThrow(TournamentNotFoundException::new);
    }

    public Tournament create(Tournament input) {
        return repository.save(input);
    }

    public Tournament edit(Tournament input, UUID id) throws TournamentNotFoundException {
        Tournament tournamentExist = repository.findById(id).orElseThrow(TournamentNotFoundException::new);
        updateEntity(tournamentExist, input);
        return repository.save(tournamentExist);
    }

    public boolean delete(UUID id) throws TournamentNotFoundException {
        repository.findById(id).orElseThrow(TournamentNotFoundException::new);
        repository.deleteById(id);
        return true;
    }

    private void updateEntity(Tournament tournament, Tournament input) {
        tournament.setName(input.getName() != null ? input.getName() : tournament.getName());
        tournament.setRoundNumber(input.getRoundNumber() != 0 ? input.getRoundNumber() : tournament.getRoundNumber());
        tournament.setRounds(input.getRounds() != null ? input.getRounds() : tournament.getRounds());
    }
}
