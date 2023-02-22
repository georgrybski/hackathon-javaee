package com.stefanini.security;

import com.stefanini.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class LoginValidator {
    @Inject
    private PasswordValidator passwordValidator;
    public boolean validateLogin(User user, String password) {
        return passwordValidator.validatePassword(user, password);
    }

}
