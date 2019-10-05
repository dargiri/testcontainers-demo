package com.example.demo;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@AllArgsConstructor
@Component
public class Bootstrap {

    private final PlayerRepository playerRepository;

    @PostConstruct
    void init() {
        if(playerRepository.count() == 0) {
            IntStream.range(0, 10).forEach(this::createPlayer);
        }
    }

    private Player createPlayer(int i) {
        return playerRepository.save(Player.builder()
                .nickName("p"+i)
                .firstName("FirstName "+i)
                .lastName("LastName "+i)
                .build());
    }
}
