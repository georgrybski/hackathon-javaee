package com.stefanini.dao;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UserDAO extends GenericDAO<User, Long> {

    public List<User> findUsersByBirthMonth(Integer requestedMonth) {
        int month = (requestedMonth == null) ? LocalDate.now().getMonthValue() : requestedMonth;
        return createQuery("SELECT u FROM User u WHERE MONTH(u.birthDate) = :currentMonth")
                .setParameter("currentMonth", month).getResultList();
    }

    public List<User> findUserByNameStartingWith(String letter) {
        return createQuery("SELECT u FROM User u WHERE u.name LIKE :letter")
                .setParameter("letter", letter + "%")
                .getResultList();
    }

    public List<String> findAllEmailProviders() {
        return em.createQuery("SELECT DISTINCT SUBSTRING(email, LOCATE('@', email) + 1) AS email_provider FROM User", String.class).getResultList();
    }

    public User findUserById(Long id) {
        return createQuery("SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public User findUserByEmail(String email) {
        return createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    public boolean createUser(UserCreationDTO userCreationDTO) {
        boolean success;
        try {
            save(new User(userCreationDTO));
            success = true;
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public boolean deleteUser(Long id) {
        boolean success;
        try {
            delete(id);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        return success;
    }
}
