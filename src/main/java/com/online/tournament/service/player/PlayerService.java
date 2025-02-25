package com.online.tournament.service.player;

import java.util.List;
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

    public Player getById(UUID id) throws PlayerNotFoundException {
        return repository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
    }

    public Player getByEmail(String email) throws PlayerNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
    }

    public Player create(Player input) throws PlayerAlreadyExistsException {
        if (repository.findByEmail(input.getEmail()).isPresent()) {
            throw new PlayerAlreadyExistsException("Player with email: " + input.getEmail() + " already exists");
        }
        return repository.save(input);
    }

    public Player edit(Player input, UUID id) throws PlayerNotFoundException {
        Player player = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        updateEntity(player, input);
        return repository.save(player);
    }

    public boolean delete(UUID id) {
        repository.findById(id).ifPresent(repository::delete);
        return true;
    }

    private void updateEntity(Player player, Player input) {
        player.setEmail(input.getEmail() != null ? input.getEmail() : player.getEmail());
        player.setName(input.getName() != null ? input.getName() : player.getName());
        player.setRanking(input.getRanking() != 0 ? input.getRanking() : player.getRanking());
    }
}
