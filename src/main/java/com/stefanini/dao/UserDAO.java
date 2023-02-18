package com.stefanini.dao;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.model.User;
import com.stefanini.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserDAO extends GenericDAO<User, Long> {

    Logger logger = LoggerFactory.getLogger(UserDAO.class);

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
        User user;
        try {
            user = findById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            user = null;
        }
        return user;
    }

    public User findUserByEmail(String email) {
        return createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    public boolean deleteUser(Long id) {
        boolean success;
        try {
            delete(id);
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = false;
        }
        return success;
    }

    public boolean createUser(UserCreationDTO userCreationDTO) {
        boolean success;
        try {
            save(new User(userCreationDTO));
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = false;
        }
        return success;
    }

    @Transactional
    public boolean updateUser(Long id, UserCreationDTO userCreationDTO) {
        boolean success;
        User user = new User(userCreationDTO);
        user.setId(id);
        try {
            update(user);
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = false;
        }
        return success;
    }

    @Transactional
    public boolean patchUser(Long id, Map<String, Object> patchData) {
        boolean success;
        try {
            User user = findById(id);
            patchData.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        user.setName((String) value);
                        break;
                    case "login":
                        user.setLogin((String) value);
                        break;
                    case "password":
                        user.setSalt(PasswordUtils.generateSalt());
                        user.setPassword(PasswordUtils.hashPassword((String) value, user.getSalt()));
                        break;
                    case "email":
                        user.setEmail((String) value);
                        break;
                    case "birthDate":
                        user.setBirthDate(LocalDate.parse((String) value));
                        break;
                    default:
                        throw new InvalidParameterException();
                }
            });
            em.merge(user);
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            success = false;
        }
        return success;
    }
}
