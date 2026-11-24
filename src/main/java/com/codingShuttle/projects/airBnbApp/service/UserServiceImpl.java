package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.entity.User;
import com.codingShuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingShuttle.projects.airBnbApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id ")) ;
    }
}
