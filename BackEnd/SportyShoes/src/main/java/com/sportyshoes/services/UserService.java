package com.sportyshoes.services;

import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){return  userRepository.findAll();}

    @Transactional
    public User saveUser(User user){
        return  userRepository.save(user);
    }

    public User findUser(String email){ return userRepository.findByEmail(email);}

    public Optional<User> findUserById(int id){ return  userRepository.findById(id);}


}
