package com.example.remind_app.service;

import com.example.remind_app.model.Usuario;
import com.example.remind_app.repository.UserRepository;
import com.example.remind_app.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Usuario userSave(UserRegistrationDto userRegistrationDto){
        Usuario user = new Usuario(
                userRegistrationDto.getNombreUsuario(),
                userRegistrationDto.getCorreo());
        return userRepository.save(user);
    }
}
