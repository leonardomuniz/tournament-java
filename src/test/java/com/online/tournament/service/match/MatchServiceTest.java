package com.online.tournament.service.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.online.tournament.model.Match;
import com.online.tournament.model.Player;
import com.online.tournament.model.Round;
import com.online.tournament.repository.MatchRepository;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @InjectMocks
    MatchService service;

    @Mock
    MatchRepository repository;

    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId(UUID.randomUUID());
        match.setPlayerOne(new Player());
        match.setPlayerTwo(new Player());
        match.setWinner(new Player());
        match.setRound(new Round());
        match.setResult("2-1");
    }

    @Test
    void findAllMatches() {
        when(repository.findAll()).thenReturn(Collections.singletonList(match));

        List<Match> matchs = service.findAll();

        assertEquals(Collections.singletonList(match), matchs);
        assertEquals(1, matchs.size());
    }
}
