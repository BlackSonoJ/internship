package org.example.userservice.api.assemblers;

import org.example.userservice.api.controllers.UserController;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.api.models.user.UserModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, UserModel> {

    @Override
    public UserModel toModel(UserDto userDto) {
        var model = new UserModel(userDto);
        model.add(
                linkTo(methodOn(UserController.class)
                                .getUserById(userDto.id())
                ).withSelfRel()
        );
        return model;
    }
}
