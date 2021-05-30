package ru.eugene.instazoo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eugene.instazoo.dto.UserDTO;
import ru.eugene.instazoo.entity.User;
import ru.eugene.instazoo.entity.enums.ERole;
import ru.eugene.instazoo.exceptions.UserExistException;
import ru.eugene.instazoo.payload.request.SignupRequest;
import ru.eugene.instazoo.repository.UserRepository;

import java.security.Principal;
import java.util.Collections;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = User.builder()
                .email(userIn.getEmail())
                .name(userIn.getFirstname())
                .lastname(userIn.getLastname())
                .username(userIn.getUsername())
                .password(passwordEncoder.encode(userIn.getPassword()))
                .roles(Collections.singleton(ERole.ROLE_USER))
                .build();

        try {
            log.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exists. Check credentials!");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
