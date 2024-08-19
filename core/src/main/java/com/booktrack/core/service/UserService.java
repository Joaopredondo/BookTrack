package com.booktrack.core.service;

import com.booktrack.core.dto.UserDTO;
import com.booktrack.core.model.User;
import com.booktrack.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
            .map(user -> {
                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                user.setPhone(userDTO.getPhone());
                return convertToDTO(userRepository.save(user));
            }).orElseThrow(() -> new RuntimeException("Usuário não encontrado" + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        return userDTO;
    }
}
