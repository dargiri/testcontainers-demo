package com.example.demo.rest;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerEndpoint {

    private final PlayerRepository playerRepository;

    @GetMapping("")
    public List<Player> listAll() {
        return playerRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") Long id) {
        return playerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
