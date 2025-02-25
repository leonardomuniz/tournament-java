package com.online.tournament.service.match;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.match.MatchDto;
import com.online.tournament.DTO.player.PlayerDto;
import com.online.tournament.DTO.round.RoundDto;
import com.online.tournament.DTO.tournament.TournamentDto;
import com.online.tournament.model.Match;
import com.online.tournament.model.Player;
import com.online.tournament.model.Round;
import com.online.tournament.model.Tournament;
import com.online.tournament.repository.MatchRepository;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
import com.online.tournament.service.exceptions.round.RoundNotFoundException;
import com.online.tournament.service.player.PlayerService;
import com.online.tournament.service.round.RoundService;
import com.online.tournament.service.tournament.TournamentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final MatchRepository repository;

    private final PlayerService playerService;

    private final RoundService roundService;

    private final TournamentService tournamentService;

    public List<MatchDto> findAll() {
        List<Match> matches = repository.findAll();
        return matches.stream().map(this::toDto).collect(Collectors.toList());
    }

    public MatchDto getById(UUID id) throws MatchNotFoundException {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        return toDto(match);
    }

    public MatchDto create(Match input) throws RoundNotFoundException {
        Match match = repository.save(input);
        return toDto(match);
    }

    public MatchDto edit(Match input, UUID id) throws MatchNotFoundException, RoundNotFoundException {
        Match match = repository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        updateEntity(match, toDto(input));
        repository.save(match);
        return toDto(match);
    }

    public boolean delete(UUID id) throws MatchNotFoundException {
        repository.findById(id).ifPresent(repository::delete);
        return true;
    }

    private void updateEntity(Match match, MatchDto input) {
        match.setRound(roundToEntity(roundService.getById(input.getRoundId())));
        match.setPlayerOne(playerToEntity(playerService.getById(input.getPlayerOneId())));
        match.setPlayerTwo(playerToEntity(playerService.getById(input.getPlayerTwoId())));
        match.setWinner(playerToEntity(playerService.getById(input.getWinnerId())));
        match.setResult(input.getResult());
    }

    private MatchDto toDto(Match match) {
        return MatchDto.builder()
                .id(match.getId())
                .roundId(match.getRound().getId())
                .playerOneId(match.getPlayerOne().getId())
                .playerTwoId(match.getPlayerTwo().getId())
                .winnerId(match.getWinner().getId())
                .result(match.getResult())
                .build();
    }

    private Player playerToEntity(PlayerDto input) {
        Player player = new Player();

        player.setId(input.getId());
        player.setEmail(input.getEmail());
        player.setName(input.getName());
        player.setRanking(input.getRanking());

        return player;
    }

    private Round roundToEntity(RoundDto input) {
        Round round = new Round();

        round.setRound(input.getRound());
        round.setTournament(tournamentToEntity(tournamentService.getById(input.getTournamentId())));
        round.setMatches(input.getMatches().stream()
                .map(matchId -> repository.findById(matchId)
                        .orElseThrow(() -> new MatchNotFoundException("Match not found")))
                .collect(Collectors.toList()));

        return round;
    }

    private Tournament tournamentToEntity(TournamentDto input) {
        Tournament tournament = new Tournament();

        tournament.setName(input.getName());
        tournament.setRoundNumber(input.getRoundNumber());
        tournament.setRounds(input.getRounds());
        tournament.setPlayers(input.getPlayers());
        tournament.setMatches(input.getMatches().stream()
                .map(matchId -> repository.findById(matchId)
                        .orElseThrow(() -> new MatchNotFoundException("Match not found")))
                .collect(Collectors.toList()));

        return tournament;
    }
}
