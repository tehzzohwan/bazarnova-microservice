package com.tehzzcode.identity_service.service;

import com.tehzzcode.identity_service.dto.GetTokenDTO;
import com.tehzzcode.identity_service.dto.UserIdentityDTO;
import com.tehzzcode.identity_service.entity.UserIdentity;

public interface AuthService {
    String registerUser(UserIdentityDTO userIdentityDTO);
    String getToken(GetTokenDTO getTokenDTO);
    void validateToken(String token);
}
