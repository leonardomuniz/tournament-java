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

import com.online.tournament.model.Player;
import com.online.tournament.service.exceptions.player.PlayerAlreadyExistsException;
import com.online.tournament.service.exceptions.player.PlayerNotFoundException;
import com.online.tournament.service.player.PlayerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/player")
@RequiredArgsConstructor
public class PlayerController {

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private final PlayerService service;

    @GetMapping("/")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.getById(id));
        } catch (PlayerNotFoundException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Player> getPlayerByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(service.getByEmail(email));
    }

    @PostMapping("/")
    public ResponseEntity<Player> createPlayer(@RequestBody Player input) {
        System.out.println(input);
        try {
            return ResponseEntity.ok().body(service.create(input));
        } catch (PlayerAlreadyExistsException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player input, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.edit(input, id));
        } catch (PlayerNotFoundException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePlayer(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(service.delete(id));
        } catch (Exception error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
