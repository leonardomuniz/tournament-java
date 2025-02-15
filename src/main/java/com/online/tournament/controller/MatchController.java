package com.online.tournament.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.tournament.DTO.MatchDTO;
import com.online.tournament.model.Match;
import com.online.tournament.service.exceptions.match.MatchNotFoundException;
import com.online.tournament.service.exceptions.round.RoundNotFoundException;
import com.online.tournament.service.match.MatchService;
import com.online.tournament.service.player.PlayerService;
import com.online.tournament.service.round.RoundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/match")
@RequiredArgsConstructor
public class MatchController {

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private final MatchService service;
    private final PlayerService playerService;
    private final RoundService roundService;

    @GetMapping("/")
    public ResponseEntity<List<Match>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> find(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.getById(id));
        } catch (MatchNotFoundException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Match> create(@RequestBody MatchDTO input) {
        try {
            Match match = new Match();
            match.setPlayerOne(playerService.getById(input.getPlayerOneId()));
            match.setPlayerTwo(playerService.getById(input.getPlayerTwoId()));
            match.setResult(input.getResult());
            if (input.getRoundId() != null) {
                match.setRound(roundService.getById(input.getRoundId()));
            }

            return ResponseEntity.ok().body(service.create(match));
        } catch (RoundNotFoundException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception error) {
            logger.error(error.getMessage(), error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Match> update(@RequestBody Match input, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.edit(input, id));
        } catch (MatchNotFoundException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.delete(id));
        } catch (Exception error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
