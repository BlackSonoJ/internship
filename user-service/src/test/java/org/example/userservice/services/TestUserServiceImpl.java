package org.example.userservice.services;


import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.common.exceptions.EntityNotFoundException;
import org.example.userservice.domain.entities.User;
import org.example.userservice.infrastructure.repositories.UserRepository;
import org.example.userservice.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserServiceImpl {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetUserByIdSuccess() {
        User user = new User("test", (short) 23, "example@mail.com");
        user.setId(1L);
        user.setCreatedAt(Instant.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(1L);

        assertEquals(1L, result.id());
        assertEquals("test", result.name());

        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    void testGetAllUsers() {
        User u1 = new User("test1", (short) 23, "example1@mail.com");
        User u2 = new User("test2", (short) 32, "example2@mail.com");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        List<UserDto> list = userService.getAllUsers();

        assertEquals(2, list.size());
        assertEquals("test1", list.get(0).name());
        assertEquals("test2", list.get(1).name());

        verify(userRepository).findAll();
    }

    @Test
    void testCreateUser() {
        PostOrPutUserDto dto = new PostOrPutUserDto("test", (short) 23, "exapmle@mail.com");

        User saved = new User(dto.name(), dto.age(), dto.email());
        saved.setId(10L);

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserDto result = userService.createUser(dto);

        assertEquals(10L, result.id());
        assertEquals("test", result.name());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUserSuccess() {
        PostOrPutUserDto dto = new PostOrPutUserDto("test", (short) 23, "example@mail.com");

        User existing = new User("prev", (short) 33, "prev_example@mail.com");
        existing.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        UserDto result = userService.updateUser(1L, dto);

        assertEquals("test", result.name());
        assertEquals(23, result.age());
        assertEquals("example@mail.com", result.email());

        verify(userRepository).save(existing);
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L,
                        new PostOrPutUserDto("test", (short) 23, "exapmle@mail.com")));
    }

    @Test
    void testDeleteUserSuccess() {
        User existing = new User();
        existing.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        userService.deleteUser(1L);

        verify(userRepository).delete(existing);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(1L));
    }

}