package com.stefanini.service;

import com.stefanini.dao.UserDAO;
import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.model.User;

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

    public List<UserRetrievalDTO> listUsers() {
        return userDAO.listAll().stream()
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
        return userDAO.deleteUser(id);
    }

    public boolean patchUser(Long id, Map<String, Object> patchData) {
        return userDAO.patchUser(id, patchData);
    }

    public boolean updateUser(Long id, UserCreationDTO userCreationDTO) {
        return userDAO.updateUser(id, userCreationDTO);
    }
}
