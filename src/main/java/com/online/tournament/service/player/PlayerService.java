package com.online.tournament.service.player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.online.tournament.DTO.player.PlayerDto;
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

    public List<PlayerDto> findAll() {
        List<Player> players = repository.findAll();
        return players.stream().map(this::toDto).collect(Collectors.toList());
    }

    public PlayerDto getById(UUID id) throws PlayerNotFoundException {
        Player player = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return toDto(player);
    }

    public PlayerDto getByEmail(String email) throws PlayerNotFoundException {
        Player player = repository.findByEmail(email)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return toDto(player);
    }

    public PlayerDto create(Player input) throws PlayerAlreadyExistsException {
        if (repository.findByEmail(input.getEmail()).isPresent()) {
            throw new PlayerAlreadyExistsException("Player with email: " + input.getEmail() + " already exists");
        }
        Player player = repository.save(input);
        return toDto(player);
    }

    public PlayerDto edit(Player input, UUID id) throws PlayerNotFoundException {
        Player player = repository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        updateEntity(player, input);
        player = repository.save(player);
        return toDto(player);
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

    private PlayerDto toDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .email(player.getEmail())
                .name(player.getName())
                .ranking(player.getRanking())
                .build();
    }
}
