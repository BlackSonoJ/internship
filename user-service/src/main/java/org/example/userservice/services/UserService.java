package org.example.userservice.services;


import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;

import java.util.List;


public interface UserService {
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto createUser(PostOrPutUserDto createUserDto);
    UserDto updateUser(Long id, PostOrPutUserDto updateUserDto);
    void deleteUser(Long id);

}
