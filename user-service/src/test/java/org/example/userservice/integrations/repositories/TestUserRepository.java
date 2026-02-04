package org.example.userservice.integrations.repositories;

import org.example.userservice.TestcontainersConfiguration;
import org.example.userservice.domain.entities.User;
import org.example.userservice.infrastructure.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
@ActiveProfiles("test")
@ImportTestcontainers(TestcontainersConfiguration.class)
@Transactional
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User result = userRepository.save(user);

        assertNotNull(result.getId());
        assertEquals("Test", result.getName());
    }

    @Test
    public void testFindAll() {
        User user1 = new User( "Test", (short) 23, "example1@mail.com");
        User user2= new User( "Test2", (short) 24, "example2@mail.com");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testFindById() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User saved = userRepository.save(user);
        var found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    public void testDeleteUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User saved = userRepository.save(user);
        var found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        userRepository.delete(found.get());

        assertFalse(userRepository.findById(saved.getId()).isPresent());
    }
}
