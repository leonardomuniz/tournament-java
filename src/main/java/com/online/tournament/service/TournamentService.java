package com.online.tournament.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.TournamentDto;
import com.online.tournament.model.Player;
import com.online.tournament.model.Round;
import com.online.tournament.model.Tournament;
import com.online.tournament.repository.PlayerRepository;
import com.online.tournament.repository.TournamentRepository;
import com.online.tournament.service.exceptions.player.PlayerNotFoundException;
import com.online.tournament.service.exceptions.tournament.TournamentNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TournamentService {

    private final TournamentRepository repository;

    private final PlayerRepository playerRepository;

    public List<TournamentDto> findAll() {
        List<Tournament> tournaments = repository.findAll();
        return tournaments.stream().map(this::toDto).collect(Collectors.toList());
    }

    public TournamentDto getById(UUID id) {
        Tournament tournament = repository.findById(id).orElseThrow(TournamentNotFoundException::new);
        return toDto(tournament);
    }

    public TournamentDto create(Tournament input) {
        Tournament tournament = new Tournament();
        tournament.setName(input.getName());
        tournament.setRoundNumber(0);
        tournament.setStarted(false);
        tournament.setOpen(false);
        tournament.setPlayers(input.getPlayers());
        tournament.setMatches(input.getMatches());

        Round round = new Round();
        round.setRound(0);
        round.setTournament(tournament);

        tournament.getRounds().add(round);

        Tournament savedTournament = repository.save(tournament);

        return toDto(savedTournament);
    }

    @Transactional
    public TournamentDto edit(Tournament input, UUID id) throws TournamentNotFoundException {
        Tournament tournamentExist = repository.findById(id)
                .orElseThrow(TournamentNotFoundException::new);

        tournamentExist.updateFrom(input);
        tournamentExist = repository.save(tournamentExist);
        return toDto(tournamentExist);
    }

    @Transactional
    public TournamentDto addPlayersToTournament(String playerEmail, UUID tournamentId)
            throws TournamentNotFoundException, PlayerNotFoundException {
        Tournament tournamentExist = repository.findById(tournamentId)
                .orElseThrow(TournamentNotFoundException::new);

        Player playerExist = playerRepository.findByEmail(playerEmail)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

        tournamentExist.getPlayers().add(playerExist);

        Tournament savedTournament = repository.save(tournamentExist);

        return toDto(savedTournament);
    }

    public boolean delete(UUID id) throws TournamentNotFoundException {
        repository.findById(id).orElseThrow(TournamentNotFoundException::new);
        repository.deleteById(id);
        return true;
    }

    private TournamentDto toDto(Tournament tournament) {
        return TournamentDto.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .roundNumber(tournament.getRoundNumber())
                .rounds(tournament.getRounds())
                .players(tournament.getPlayers())
                .matches(tournament.getMatches())
                .started(tournament.getStarted())
                .open(tournament.getOpen())
                .build();
    }
}
