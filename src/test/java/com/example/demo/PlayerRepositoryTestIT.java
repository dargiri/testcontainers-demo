package com.example.demo;

import com.example.demo.entity.Player;
import com.example.demo.it.AbstractIT;
import com.example.demo.repository.PlayerRepository;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerRepositoryTestIT extends AbstractIT {
    @Resource
    private PlayerRepository testObj;

    @PersistenceContext
    EntityManager em;

    @Test
    public void createNew() {
        Player player = testObj.saveAndFlush(Player.builder()
                .nickName("johndoe")
                .firstName("John")
                .lastName("Doe")
                .build());

        em.clear();

        assertThat(player.getId()).isNotNull();

        Optional<Player> foundObj = testObj.findById(player.getId());

        assertThat(foundObj).isPresent();

        Player obj = foundObj.get();
        assertThat(obj.getFirstName()).isEqualTo("John");
        assertThat(obj.getLastName()).isEqualTo("Doe");
    }
}
