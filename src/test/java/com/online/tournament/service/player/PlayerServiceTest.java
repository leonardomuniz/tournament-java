package com.online.tournament.service.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.online.tournament.model.Player;
import com.online.tournament.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository repository;

    @InjectMocks
    private PlayerService service;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setId(UUID.randomUUID());
        player.setName("Test Player");
        player.setEmail("test@example.com");
        player.setRanking(1);
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(player));

        List<Player> players = service.findAll();

        assertFalse(players.isEmpty());
        assertEquals(1, players.size());
        assertEquals("Test Player", players.get(0).getName());
    }

    @Test
    void testGetById() {
        UUID id = player.getId();
        when(repository.findById(id)).thenReturn(Optional.of(player));

        Player foundPlayer = service.getById(id);

        assertNotNull(foundPlayer);
        assertEquals("Test Player", foundPlayer.getName());
    }

    @Test
    void testGetByEmail() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(player));

        Player foundPlayer = service.getByEmail("test@example.com");

        assertNotNull(foundPlayer);
        assertEquals("Test Player", foundPlayer.getName());
    }

    @Test
    void testCreate() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(repository.save(player)).thenReturn(player);

        Player createdPlayer = service.create(player);

        assertNotNull(createdPlayer);
        assertEquals("Test Player", createdPlayer.getName());
    }

    @Test
    void testEdit() {
        UUID id = player.getId();
        when(repository.findById(id)).thenReturn(Optional.of(player));
        when(repository.save(player)).thenReturn(player);

        Player input = new Player();
        input.setEmail("updated@example.com");
        input.setName("Updated Player");
        input.setRanking(2);

        Player updatedPlayer = service.edit(input, id);

        assertNotNull(updatedPlayer);
        assertEquals("Updated Player", updatedPlayer.getName());
        assertEquals("updated@example.com", updatedPlayer.getEmail());
        assertEquals(2, updatedPlayer.getRanking());
    }

    @Test
    void testDelete() {
        UUID id = player.getId();
        when(repository.findById(id)).thenReturn(Optional.of(player));
        doNothing().when(repository).deleteById(id);

        boolean result = service.delete(id);

        assertTrue(result);
        verify(repository, times(1)).deleteById(id);
    }
}
