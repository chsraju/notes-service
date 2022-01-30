package com.disqo.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.disqo.notes.model.User;
import com.disqo.notes.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
    	log.info(" Retrive the Users for the email: {}", email);
        User user = (userRepository.findByEmailAndPassword(email, password));
        if(user == null) {
        	throw new ResourceNotFoundException("User is not found for emailId: "+email);
        }
        return user;
    }
}
