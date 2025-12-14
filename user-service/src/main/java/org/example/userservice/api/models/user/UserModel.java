package org.example.userservice.api.models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.userservice.api.dto.user.UserDto;
import org.springframework.hateoas.RepresentationModel;

@RequiredArgsConstructor
@Getter
public class UserModel extends RepresentationModel<UserModel> {
    private final UserDto user;
}
