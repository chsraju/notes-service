package com.disqo.notes.service;

import com.disqo.notes.model.User;


public interface UserService {
    public User getUserByEmailAndPassword(String email, String password);
}
