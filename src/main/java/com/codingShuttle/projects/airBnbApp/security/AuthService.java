package com.codingShuttle.projects.airBnbApp.security;


import com.codingShuttle.projects.airBnbApp.dto.LogInDto;
import com.codingShuttle.projects.airBnbApp.dto.SignUpRequestDto;
import com.codingShuttle.projects.airBnbApp.dto.UserDto;
import com.codingShuttle.projects.airBnbApp.entity.User;
import com.codingShuttle.projects.airBnbApp.entity.enums.Role;
import com.codingShuttle.projects.airBnbApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper ;
    private final PasswordEncoder passwordEncoder ;
    private final AuthenticationManager authenticationManager ;
    private final JWTService jwtService ;




    // SIGNUP METHOD
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);

        if(user != null) {
            throw new RuntimeException("User is alreafy present in this email id ");

        }
        User newUser =modelMapper.map(signUpRequestDto , User.class) ;
        user.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser) ;

        return modelMapper.map(newUser , UserDto.class);

    }

    public String[] login(LogInDto logInDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                logInDto.getEmail() , logInDto.getPassword()
        ));

       User user = (User) authentication.getPrincipal() ;
       String arr[] = new String[2] ;
       arr[0] = jwtService.generateAccessToken(user);
       arr[1] = jwtService.generateRefreshToken(user) ;

       return arr ;

    }


}
