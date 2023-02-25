package com.stefanini.exception;


public class UserNotFoundException extends RuntimeException {
    private UserNotFoundException(String identifier, String value) {
        super(String.format("User with %s %s not found", identifier, value));
    }

    public static UserNotFoundException idNotFound (Long id) {
        return new UserNotFoundException("id", id.toString());
    }

    public static UserNotFoundException loginNotFound (String login) {
        return new UserNotFoundException("login", login);
    }

    public static UserNotFoundException emailNotFound (String email) {
        return new UserNotFoundException("email", email);
    }
}
