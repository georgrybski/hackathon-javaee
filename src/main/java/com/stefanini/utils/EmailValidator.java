package com.stefanini.utils;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailValidator {

    private final String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public boolean isEmail(String loginOrEmail) {
        return loginOrEmail.matches(emailPattern);
    }
}
