package com.tehzzcode.identity_service.controller;

import com.tehzzcode.identity_service.dto.GetTokenDTO;
import com.tehzzcode.identity_service.dto.UserIdentityDTO;
import com.tehzzcode.identity_service.entity.UserIdentity;
import com.tehzzcode.identity_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserIdentityDTO userIdentityDTO) {
        return authService.registerUser(userIdentityDTO);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody GetTokenDTO getTokenDTO) {
        return authService.getToken(getTokenDTO);
    }


    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
         authService.validateToken(token);
         return "Token is valid";
    }
}
