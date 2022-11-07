package com.example.remind_app.service;

import com.example.remind_app.model.Usuario;
import com.example.remind_app.web.dto.UserRegistrationDto;

public interface UserService {
    Usuario userSave(UserRegistrationDto userRegistrationDto);
}
