package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id);
        return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        boolean isExistedEmail = false;
        isExistedEmail = userRepository.existsByEmail(user.getEmail());

        if (isExistedEmail) {
            throw new RuntimeException("이메일이 존재합니다.");
        }

        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        boolean isExistedEmail = false;
        isExistedEmail = userRepository.existsByEmail(user.getEmail());

        if (isExistedEmail) {
            throw new RuntimeException("이메일이 존재합니다.");
        }

        User existedUser = userRepository.update(user);
        return existedUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> searchUserByName(String name) {
        return userRepository.searchUserByName(name);
    }

    @Override
    public Integer updateUserEmail(Long id, String email) {
        boolean isExistedEmail = userRepository.existsByEmail(email);
        if (isExistedEmail) {
            throw new RuntimeException("이메일이 존재합니다 : " + email);
        }
        return userRepository.updateEmail(id, email);
    }
}
