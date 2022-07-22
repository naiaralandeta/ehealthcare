package com.dell.ehealthcare.services;

import com.dell.ehealthcare.model.User;
import com.dell.ehealthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

}
