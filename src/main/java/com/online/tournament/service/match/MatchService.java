package com.online.tournament.service.match;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Match;
import com.online.tournament.model.Player;
import com.online.tournament.repository.MatchRepository;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
import com.online.tournament.service.player.PlayerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository repository;
    private final PlayerService playerService;

    public List<Match> findAll() {
        return repository.findAll();
    }

    public Match getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
    }

    public Match create(Match input) {
        System.out.println("AAAAAAAAAAAAAAAAA" + input);
        if (input.getPlayerOne() == null || input.getPlayerOne().getId() == null) {
            throw new IllegalArgumentException("PlayerOne não pode ser nulo e deve ter um ID válido.");
        }

        if (input.getPlayerTwo() == null || input.getPlayerTwo().getId() == null) {
            throw new IllegalArgumentException("PlayerTwo não pode ser nulo e deve ter um ID válido.");
        }

        Player playerOne = playerService.getById(input.getPlayerOne().getId());
        Player playerTwo = playerService.getById(input.getPlayerTwo().getId());

        input.setPlayerOne(playerOne);
        input.setPlayerTwo(playerTwo);

        return repository.save(input);
    }

    public Match edit(Match input, UUID id) {
        Optional<Match> matchExist = Optional.ofNullable(getById(id));

        if (matchExist.isEmpty()) {
            throw new MatchNotFoundException();
        }

        Match existingMatch = matchExist.get();

        existingMatch.setResult(input.getResult() != null ? input.getResult() : existingMatch.getResult());
        existingMatch.setRound(input.getRound() != null ? input.getRound() : existingMatch.getRound());
        existingMatch.setPlayerOne(input.getPlayerOne() != null ? input.getPlayerOne() : existingMatch.getPlayerOne());
        existingMatch.setPlayerTwo(input.getPlayerTwo() != null ? input.getPlayerTwo() : existingMatch.getPlayerTwo());
        existingMatch.setWinner(input.getWinner() != null ? input.getWinner() : existingMatch.getWinner());

        return repository.save(existingMatch);
    }

    public boolean delete(UUID id) {
        Optional<Match> match = Optional.ofNullable(repository.findById(id).get());

        if (match.isEmpty()) {
            throw new MatchNotFoundException();
        }
        repository.deleteById(id);

        return true;
    }
}
