package com.online.tournament.service.match;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.match.MatchDto;
import com.online.tournament.mapper.MatchMapper;
import com.online.tournament.mapper.PlayerMapper;
import com.online.tournament.mapper.RoundMapper;
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

    private final MatchMapper matchMapper;

    private final RoundMapper roundMapper;

    private final PlayerMapper playerMapper;

    public List<MatchDto> findAll() {
        List<Match> matches = repository.findAll();
        return matches.stream().map(matchMapper::toDto).toList();
    }

    public MatchDto getById(UUID id) throws MatchNotFoundException {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException());

        return matchMapper.toDto(match);
    }

    public MatchDto create(Match input) throws RoundNotFoundException {
        Match match = new Match();
        match.setRound(roundMapper.toEntity(roundService.getById(input.getRound().getId())));
        match.setPlayerOne(playerMapper.toEntity(playerService.getById(input.getPlayerOne().getId())));
        match.setPlayerTwo(playerMapper.toEntity(playerService.getById(input.getPlayerTwo().getId())));
        match.setWinner(playerMapper.toEntity(playerService.getById(input.getWinner().getId())));
        match.setResult(input.getResult());

        match = repository.save(match);

        return matchMapper.toDto(match);
    }

    public MatchDto edit(Match input, UUID id) throws MatchNotFoundException, RoundNotFoundException {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException());
        updateEntity(match, input);
        match = repository.save(match);

        return matchMapper.toDto(match);
    }

    public boolean delete(UUID id) throws MatchNotFoundException {
        repository.findById(id).ifPresent(repository::delete);
        return true;
    }

    private void updateEntity(Match match, Match input) {
        match.setRound(roundMapper.toEntity(roundService.getById(input.getRound().getId())));
        match.setPlayerOne(playerMapper.toEntity(playerService.getById(input.getPlayerOne().getId())));
        match.setPlayerTwo(playerMapper.toEntity(playerService.getById(input.getPlayerTwo().getId())));
        match.setWinner(playerMapper.toEntity(playerService.getById(input.getWinner().getId())));
        match.setResult(input.getResult());
    }

}
