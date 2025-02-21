package com.online.tournament.service.player;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.player.PlayerDto;
import com.online.tournament.mapper.PlayerMapper;
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

    private final PlayerMapper mapper;

    public List<PlayerDto> findAll() {
        List<Player> players = repository.findAll();
        return players.stream().map(mapper::toDto).toList();
    }

    public PlayerDto getById(UUID id) throws PlayerNotFoundException {
        Player player = repository.findById(id).orElse(null);

        return mapper.toDto(player);
    }

    public PlayerDto getByEmail(String email) throws PlayerNotFoundException {
        try {
            return mapper.toDto(repository.findByEmail(email).get());
        } catch (PlayerNotFoundException error) {
            System.err.println(error.getMessage());
            return null;
        }
    }

    public PlayerDto create(Player input) throws PlayerAlreadyExistsException {
        Player player = repository.findByEmail(input.getEmail())
                .orElseThrow(() -> new PlayerAlreadyExistsException(
                        "Player with email: " + input.getEmail() + "already exists"));

        return mapper.toDto(repository.save(player));
    }

    public PlayerDto edit(Player input, UUID id) throws PlayerNotFoundException {
        Player player = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException());

        updateEntity(player, input);
        player = repository.save(player);

        return mapper.toDto(player);
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
