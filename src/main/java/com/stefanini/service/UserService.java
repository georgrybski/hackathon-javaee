package com.stefanini.service;

import com.stefanini.dao.UserDAO;
import com.stefanini.dto.LoginRequest;
import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.exception.UserNotFoundException;
import com.stefanini.model.User;
import com.stefanini.security.LoginValidator;
import com.stefanini.utils.EmailValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    private UserDAO userDAO;
    @Inject
    private LoginValidator loginValidator;
    @Inject
    private EmailValidator emailValidator;

    public List<UserRetrievalDTO> listUsers(Long id, String name, String login, String email, Integer month, String emailProvider) {
        return userDAO.getUsers(id, name, login, email, month, emailProvider).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> findUsersByBirthMonth(Integer month) {
        return userDAO.findUsersByBirthMonth(month).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserRetrievalDTO> findUserByNameStartingWith(String letter) {
        return userDAO.findUserByNameStartingWith(letter).stream()
                .map(UserRetrievalDTO::new)
                .collect(Collectors.toList());
    }

    public boolean validateLoginRequest(LoginRequest loginRequest) {
        boolean isEmail = emailValidator.isEmail(loginRequest.getLogin());
        User user = isEmail ? getUserByEmailOrThrow(loginRequest.getLogin()) : getUserByLoginOrThrow(loginRequest.getLogin());
        return loginValidator.validateLogin(user, loginRequest.getPassword());
    }

    public List<String> getEmailProviders() {
        return userDAO.findAllEmailProviders();
    }

    public Optional<UserRetrievalDTO> findUserById(Long id) {
        return userDAO.findUserById(id).map(UserRetrievalDTO::new);
    }

    public boolean createUser(UserCreationDTO userCreationDTO) {
        return userDAO.createUser(userCreationDTO);
    }

    public boolean deleteUser(Long id) {
        return userDAO.deleteUser(getUserByIdOrThrow(id));
    }

    private User getUserByIdOrThrow(Long id) {
        Optional<User> user = userDAO.findUserById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw UserNotFoundException.idNotFound(id);
        }
    }

    private User getUserByEmailOrThrow(String email) {
        Optional<User> user = userDAO.findUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw UserNotFoundException.emailNotFound(email);
        }
    }

    private User getUserByLoginOrThrow(String login) {
        Optional<User> user = userDAO.findUserByLogin(login);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw UserNotFoundException.loginNotFound(login);
        }
    }

    public void deleteUsersBatch(List<Long> ids) {
        userDAO.deleteUsersBatch(ids);
    }

    public boolean patchUser(Long id, Map<String, Object> patchData) {
        return userDAO.patchUser(getUserByIdOrThrow(id), patchData);
    }

    public boolean updateUser(Long id, UserCreationDTO userCreationDTO) {
        return userDAO.updateUser(id, userCreationDTO);
    }

    //  Method to check if a certain login is availiable (isLoginAvailable)
    public boolean isLoginAvailable(String login) {
        return userDAO.findUserByLogin(login).isEmpty();
    }
}
