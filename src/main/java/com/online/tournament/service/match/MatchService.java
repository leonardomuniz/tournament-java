package com.online.tournament.service.match;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Match;
import com.online.tournament.repository.MatchRepository;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
import com.online.tournament.service.exceptions.round.RoundNotFoundException;
import com.online.tournament.service.player.PlayerService;
import com.online.tournament.service.round.RoundService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository repository;
    private final PlayerService playerService;
    private final RoundService roundService;

    public List<Match> findAll() {
        return repository.findAll();
    }

    public Match getById(UUID id) throws MatchNotFoundException {
        return repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
    }

    public Match create(Match input) throws RoundNotFoundException {
        Match match = new Match();
        updateEntity(match, input);
        return repository.save(match);
    }

    public Match edit(Match input, UUID id) throws MatchNotFoundException, RoundNotFoundException {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
        updateEntity(match, input);
        return repository.save(match);
    }

    public boolean delete(UUID id) throws MatchNotFoundException {
        repository.findById(id).ifPresent(repository::delete);
        return true;
    }

    private void updateEntity(Match match, Match input) {
        match.setRound(roundService.getById(input.getRound().getId()));
        match.setPlayerOne(playerService.getById(input.getPlayerOne().getId()));
        match.setPlayerTwo(playerService.getById(input.getPlayerTwo().getId()));
        match.setWinner(playerService.getById(input.getWinner().getId()));
        match.setResult(input.getResult());
    }
}
