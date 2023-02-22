package com.stefanini.dao;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.model.User;
import com.stefanini.security.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;

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

    public LinkedHashMap<String, Long> findEmailProvidersWithCount() {
        return em.createQuery("SELECT SUBSTRING(email, LOCATE('@', email) + 1) AS email_provider, COUNT(*) AS incidences FROM User GROUP BY email_provider ORDER BY incidences DESC", Object[].class)
                .getResultStream().collect(LinkedHashMap::new, (map, obj) -> map.put((String) obj[0], (Long) obj[1]), LinkedHashMap::putAll);
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(findById(id));
    }

    public Optional<User> findUserByEmail(String email) {
        return createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    @Transactional
    public boolean deleteUser(User user) {
        delete(user.getId());
        return true;
    }

    public boolean createUser(UserCreationDTO userCreationDTO) {
        save(new User(userCreationDTO));
        return true;
    }

    @Transactional
    public boolean updateUser(Long id, UserCreationDTO userCreationDTO) {
        User user = new User(userCreationDTO);
        user.setId(id);
        update(user);
        return true;
    }

    @Transactional
    public boolean patchUser(User user, Map<String, Object> patchData) {
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
                    throw new InvalidParameterException("Invalid parameter: " + key);
            }
        });
        em.merge(user);
        return true;
    }

    public Optional<User> findUserByLogin(String login) {
        return createQuery("SELECT u FROM User u WHERE u.login = :login")
                .setParameter("login", login)
                .getResultList().stream().findFirst();
    }
}
