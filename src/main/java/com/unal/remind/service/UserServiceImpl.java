package com.unal.remind.service;

import com.unal.remind.model.Usuario;
import com.unal.remind.repository.UserRepository;
import com.unal.remind.web.dto.UserRegistrationDto;
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
