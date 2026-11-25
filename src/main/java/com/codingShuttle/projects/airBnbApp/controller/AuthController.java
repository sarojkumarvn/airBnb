package com.codingShuttle.projects.airBnbApp.controller;


import com.codingShuttle.projects.airBnbApp.dto.LogInDto;
import com.codingShuttle.projects.airBnbApp.dto.LoginResponseDto;
import com.codingShuttle.projects.airBnbApp.dto.SignUpRequestDto;
import com.codingShuttle.projects.airBnbApp.dto.UserDto;
import com.codingShuttle.projects.airBnbApp.security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService ;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(SignUpRequestDto signUpRequestDto) {
        return new ResponseEntity<>(authService.signUp(signUpRequestDto) , HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LogInDto loginDto , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse) {
        String[] tokens = authService.login(loginDto);


        Cookie cookie = new Cookie("refreshToken" , tokens[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)  {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow( ()-> new AuthenticationServiceException("Refresh token not found inside the cookie"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken)) ;
    }

}
