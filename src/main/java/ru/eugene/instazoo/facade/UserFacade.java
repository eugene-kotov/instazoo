package ru.eugene.instazoo.facade;

import org.springframework.stereotype.Component;
import ru.eugene.instazoo.dto.UserDTO;
import ru.eugene.instazoo.entity.User;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());

        return userDTO;
    }
}
