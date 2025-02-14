package com.online.tournament.service.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.model.Player;
import com.online.tournament.repository.PlayerRepository;
import com.online.tournament.service.exceptions.player.PlayerAlreadyExistsException;
import com.online.tournament.service.exceptions.player.PlayerNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {

    private final PlayerRepository repository;

    public List<Player> findAll() {
        return repository.findAll();
    }

    public Player getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new PlayerNotFoundException());

    }

    public Player getByEmail(String email) {
        try {
            return repository.findByEmail(email).orElseThrow(() -> new PlayerNotFoundException());
        } catch (PlayerNotFoundException error) {
            System.err.println(error.getMessage());
            return null;
        }
    }

    public Player create(Player input) {
        Optional<Player> playerExist = Optional.ofNullable(getByEmail(input.getEmail()));

        if (playerExist.isPresent()) {
            throw new PlayerAlreadyExistsException("Player already exists with email: " + input.getEmail());
        }

        return repository.save(input);

    }

    public Player edit(Player input, UUID id) {
        Optional<Player> playerExist = Optional.ofNullable(getById(id));

        if (playerExist.isEmpty()) {
            throw new PlayerNotFoundException();
        }

        Player existingPlayer = playerExist.get();

        existingPlayer.setEmail(input.getEmail() != null ? input.getEmail() : existingPlayer.getEmail());
        existingPlayer.setName(input.getName() != null ? input.getName() : existingPlayer.getName());
        existingPlayer.setRanking(input.getRanking() != 0 ? input.getRanking() : existingPlayer.getRanking());

        return repository.save(existingPlayer);
    }

    public boolean delete(UUID id) {
        Optional<Player> player = Optional.ofNullable(repository.findById(id).get());

        if (player.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        repository.deleteById(id);

        return true;
    }
}
