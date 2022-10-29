package com.unal.remind.service;

import com.unal.remind.model.Usuario;
import com.unal.remind.web.dto.UserRegistrationDto;

public interface UserService {
    Usuario userSave(UserRegistrationDto userRegistrationDto);
}
