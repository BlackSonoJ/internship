package org.example.userservice.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.api.assemblers.UserModelAssembler;
import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.models.user.UserModel;
import org.example.userservice.services.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.getUserById(id));
    }

    @GetMapping
    public CollectionModel<UserModel> getAllUsers() {
        return CollectionModel.of(
                userService.getAllUsers()
                        .stream()
                        .map(userModelAssembler::toModel)
                        .toList(),
                linkTo(
                        methodOn(UserController.class)
                                .getAllUsers()
                ).withSelfRel()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createUser(@Valid @RequestBody PostOrPutUserDto dto) {
        return userModelAssembler.toModel(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public UserModel updateUser(
            @PathVariable Long id,
            @Valid @RequestBody PostOrPutUserDto dto
    ) {
        return userModelAssembler.toModel(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
