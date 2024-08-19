package com.booktrack.core.service;

import com.booktrack.core.dto.UserDTO;
import com.booktrack.core.model.User;
import com.booktrack.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPhone("+123456789");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("+123456789");

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUser = userService.createUser(userDTO);

        assertEquals("John Doe", createdUser.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> foundUser = userService.getUserById(1L);

        assertEquals("John Doe", foundUser.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.updateUser(1L, userDTO);
        } catch (RuntimeException e) {
            assertEquals("User not found with id 1", e.getMessage());
        }

        verify(userRepository, times(1)).findById(1L);
    }
}
