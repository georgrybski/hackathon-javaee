package com.stefanini.security;

import com.stefanini.model.User;

import javax.enterprise.context.ApplicationScoped;

import static com.stefanini.security.PasswordUtils.hashPassword;

@ApplicationScoped
public class PasswordValidator {

    public boolean validatePassword(User user, String password) {
            String hash = hashPassword(password, user.getSalt());
            return hash.equals(user.getPassword());
    }
}
