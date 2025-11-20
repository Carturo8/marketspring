package com.haru.marketspring.service;

import com.haru.marketspring.dto.user.UserCreateDTO;
import com.haru.marketspring.dto.user.UserResponseDTO;
import com.haru.marketspring.dto.user.UserUpdateDTO;
import com.haru.marketspring.entity.User;
import com.haru.marketspring.exception.ConflictException;
import com.haru.marketspring.exception.ResourceNotFoundException;
import com.haru.marketspring.mapper.UserMapper;
import com.haru.marketspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseDTOList(users);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        userRepository.findByEmail(userCreateDTO.email())
                .ifPresent(u -> {
                    throw new ConflictException("Email already in use");
                });
        userRepository.findByUsername(userCreateDTO.username())
                .ifPresent(u -> {
                    throw new ConflictException("Username already in use");
                });

        User user = userMapper.toUser(userCreateDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (userUpdateDTO.username() != null) {
            userRepository.findByUsername(userUpdateDTO.username())
                    .ifPresent(u -> {
                        if (!u.getId().equals(id)) {
                            throw new ConflictException("Username already in use");
                        }
                    });
            user.setUsername(userUpdateDTO.username());
        }

        if (userUpdateDTO.email() != null) {
            userRepository.findByEmail(userUpdateDTO.email())
                    .ifPresent(u -> {
                        if (!u.getId().equals(id)) {
                            throw new ConflictException("Email already in use");
                        }
                    });
            user.setEmail(userUpdateDTO.email());
        }

        if (userUpdateDTO.password() != null) {
            user.setPassword(userUpdateDTO.password());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
