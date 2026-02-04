package org.example.userservice.services.implementation;

import lombok.RequiredArgsConstructor;
import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.api.mappers.UserMapper;
import org.example.userservice.common.enums.Operation;
import org.example.userservice.common.exceptions.EntityNotFoundException;
import org.example.userservice.infrastructure.kafka.KafkaProducer;
import org.example.userservice.infrastructure.kafka.events.MessageDto;
import org.example.userservice.infrastructure.repositories.UserRepository;
import org.example.userservice.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
        return UserMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList()
                );
    }

    public UserDto createUser(PostOrPutUserDto userDto) {
        var createdUserDto = UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
        kafkaProducer.sendMessage("user.event",  new MessageDto(Operation.CREATE, createdUserDto.email()));
        return createdUserDto;
    }

    public UserDto updateUser(Long id, PostOrPutUserDto userDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
        UserMapper.updateEntity(user, userDto);
        return UserMapper.toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        var foundedUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        kafkaProducer.sendMessage("user.event", new MessageDto(Operation.DELETE, foundedUser.getEmail()));
        userRepository.delete(foundedUser);
    }
}
