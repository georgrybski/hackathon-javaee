package com.stefanini.service;

import com.stefanini.dao.UserDAO;
import com.stefanini.dto.LoginRequest;
import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.exception.UserNotFoundException;
import com.stefanini.model.User;
import com.stefanini.security.LoginValidator;
import com.stefanini.util.EmailValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private LoginValidator loginValidator;

    @Inject
    private EmailValidator emailValidator;

    public UserRetrievalDTO findUserById(Long id) {
        return new UserRetrievalDTO(getUserByIdOrThrow(id));
    }

    public UserRetrievalDTO findUserByLogin(String login) {
        return new UserRetrievalDTO(getUserByLoginOrThrow(login));
    }

    public UserRetrievalDTO findUserByEmail(String email) {
        return new UserRetrievalDTO(getUserByEmailOrThrow(email));
    }


    public List<UserRetrievalDTO> findUserByNameStartingWith(String letter) {
        return userDAO.findUserByNameStartingWith(letter).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> findUsersByBirthMonth(Integer month) {
        return userDAO.findUsersByBirthMonth(month).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> listUsers() {
        return userDAO.listAll().stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<String> getEmailProviders() {
        return userDAO.findAllEmailProviders();
    }

    public LinkedHashMap<String, Long> getEmailProvidersWithCount() {
        return userDAO.findEmailProvidersWithCount();
    }

    public boolean createUser(UserCreationDTO userCreationDTO) {
        return userDAO.createUser(userCreationDTO);
    }

    public boolean deleteUser(Long id) {
        return userDAO.deleteUser(getUserByIdOrThrow(id));
    }

    public boolean patchUser(Long id, Map<String, Object> patchData) {
        return userDAO.patchUser(getUserByIdOrThrow(id), patchData);
    }

    public boolean updateUser(Long id, UserCreationDTO userCreationDTO) {
        return userDAO.updateUser(id, userCreationDTO);
    }

    public boolean validateLoginRequest(LoginRequest loginRequest) {
        boolean isEmail = emailValidator.isEmail(loginRequest.getLogin());
        User user = isEmail ? getUserByEmailOrThrow(loginRequest.getLogin()) :getUserByLoginOrThrow(loginRequest.getLogin());
        return loginValidator.validateLogin(user, loginRequest.getPassword());
    }

    private User getUserByIdOrThrow(Long id) {
        Optional<User> user = userDAO.findUserById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with id \'" + id + "\' does not exist.");
        }
    }

    private User getUserByLoginOrThrow(String login) {
        Optional<User> user = userDAO.findUserByLogin(login);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with login \'" + login + "\' does not exist.");
        }
    }
    private User getUserByEmailOrThrow(String email) {
        Optional<User> user = userDAO.findUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with email \'" + email + "\' does not exist.");
        }
    }

    public List<UserRetrievalDTO> findUserByLoginContaining(String login) {
        return userDAO.findUserByLoginContaining(login).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> findUserByNameContaining(String string) {
        return userDAO.findUserByNameContaining(string).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> findUserByEmailContaining(String string) {
        return userDAO.findUserByEmailContaining(string).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }
}
