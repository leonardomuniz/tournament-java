package com.online.tournament.service.tournament;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.tournament.TournamentDto;
import com.online.tournament.model.Round;
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
        tournament.setPlayers(input.getPlayers());
        tournament.setMatches(input.getMatches());

        Round round = new Round();
        round.setRound(0);
        round.setTournament(tournament);

        tournament.getRounds().add(round);

        Tournament savedTournament = repository.save(tournament);
        System.out.println("ID do Torneio Salvo: " + savedTournament.getId());

        return toDto(savedTournament);
    }

    public TournamentDto edit(Tournament input, UUID id) throws TournamentNotFoundException {
        Tournament tournamentExist = repository.findById(id).orElseThrow(TournamentNotFoundException::new);
        updateEntity(tournamentExist, input);
        tournamentExist = repository.save(tournamentExist);
        return toDto(tournamentExist);
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

    private TournamentDto toDto(Tournament tournament) {
        return TournamentDto.builder()
                .id(tournament.getId())
                .players(tournament.getPlayers())
                .matches(tournament.getMatches())
                .name(tournament.getName())
                .roundNumber(tournament.getRoundNumber())
                .rounds(tournament.getRounds())
                .build();
    }
}
