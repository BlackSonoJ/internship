package org.example.userservice.api.controllers;

import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController  userController;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUserById() throws Exception {
        UserDto user = new UserDto(1L, "test", (short) 23,"example@mail.com", Instant.now());
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.email").value("example@mail.com"));
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, "test1", (short) 23,"example1@mail.com", Instant.now()),
                new UserDto(2L, "test2", (short) 32,"example2@mail.com", Instant.now())
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createUser() throws Exception {
        UserDto saved = new UserDto(1L, "test", (short) 23,"example@mail.com", Instant.now());
        when(userService.createUser(any(PostOrPutUserDto.class))).thenReturn(saved);

        String dtoJson = """
            {
                "name": "test",
                "age": 23,
                "email": "example@mail.com"
            }
            """;

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.email").value("example@mail.com"));

    }
    @Test
    void updateUser() throws Exception {
        UserDto updated = new UserDto(1L, "test", (short) 23,"example@mail.com", Instant.now());
        when(userService.updateUser(eq(1L), any(PostOrPutUserDto.class))).thenReturn(updated);

        String dtoJson = """
            {
                "name": "test",
                "age": 23,
                "email": "example@mail.com"
            }
            """;

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON).content(dtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.email").value("example@mail.com"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

}
