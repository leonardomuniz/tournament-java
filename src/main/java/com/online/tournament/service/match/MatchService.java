package com.online.tournament.service.match;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.match.InputMatchDTO;
import com.online.tournament.DTO.match.OutputMatchDTO;
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

    public List<OutputMatchDTO> findAll() {
        List<Match> matches = repository.findAll();
        return matches.stream().map(this::convertToDTO).toList();
    }

    public OutputMatchDTO getById(UUID id) {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException());

        return convertToDTO(match);
    }

    public OutputMatchDTO create(InputMatchDTO input) throws RoundNotFoundException {
        Match match = convertToEntity(input);
        match = repository.save(match);

        return convertToDTO(match);
    }

    public OutputMatchDTO edit(InputMatchDTO input, UUID id) throws MatchNotFoundException, RoundNotFoundException {
        Match matchExist = repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
        updateEntity(matchExist, input);
        matchExist = repository.save(matchExist);

        return convertToDTO(matchExist);
    }

    public boolean delete(UUID id) {
        repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
        repository.deleteById(id);
        return true;
    }

    private OutputMatchDTO convertToDTO(Match match) {
        return OutputMatchDTO.builder()
                .id(match.getId())
                .playerOne(match.getPlayerOne())
                .playerTwo(match.getPlayerTwo())
                .round(match.getRound().getId())
                .result(match.getResult())
                .winner(match.getWinner())
                .build();
    }

    private Match convertToEntity(InputMatchDTO input) {
        Match match = new Match();

        match.setPlayerOne(playerService.getById(input.getPlayerOneId()));
        match.setPlayerTwo(playerService.getById(input.getPlayerTwoId()));
        match.setRound(roundService.getById(input.getRoundId()));
        match.setResult(input.getResult());
        match.setWinner(playerService.getById(input.getWinnerId()));
        return match;
    }

    private void updateEntity(Match match, InputMatchDTO input) {
        match.setPlayerOne(playerService.getById(input.getPlayerOneId()));
        match.setPlayerTwo(playerService.getById(input.getPlayerTwoId()));
        match.setRound(roundService.getById(input.getRoundId()));
        match.setResult(input.getResult());
        match.setWinner(playerService.getById(input.getWinnerId()));
    }

}
