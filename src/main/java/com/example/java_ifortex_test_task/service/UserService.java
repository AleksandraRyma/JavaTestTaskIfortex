package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import com.example.java_ifortex_test_task.repository.UserRepository;
import com.example.java_ifortex_test_task.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;

    // Returns a User with the biggest amount of sessions
    public UserResponseDTO getUserWithMostSessions() {
        return userMapper.toDto(userRepository.getUserWithMostSessions());
    }

    // Returns Users that have at least 1 Mobile session
    public List<UserResponseDTO> getUsersWithAtLeastOneMobileSession() {
        List<UserResponseDTO> usersWithMobileSessions = new ArrayList<>();
        List<User> users = userRepository.getUsersWithAtLeastOneMobileSession(DeviceType.MOBILE);
        for(User user:users) {
            usersWithMobileSessions.add(userMapper.toDto(user));
        }
        return usersWithMobileSessions;
    }
}
