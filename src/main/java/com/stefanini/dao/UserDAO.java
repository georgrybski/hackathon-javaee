package com.stefanini.dao;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.model.User;
import com.stefanini.security.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(findById(id));
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email).getSingleResult());
    }

    public Optional<User> findUserByLogin(String login) {
        return createQuery("SELECT u FROM User u WHERE u.login = :login")
                .setParameter("login", login)
                .getResultList().stream().findFirst();
    }

    public boolean deleteUser(User user) {
        delete(user.getId());
        return true;
    }

    @Transactional
    public void deleteUsersBatch(List<Long> ids) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM USUARIO WHERE id IN (");
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                queryBuilder.append(",");
            }
            queryBuilder.append("?");
        }
        queryBuilder.append(")");
        Query query = em.createNativeQuery(queryBuilder.toString());
        for (int i = 0; i < ids.size(); i++) {
            query.setParameter(i + 1, ids.get(i));
        }
        query.executeUpdate();
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

    public List<User> getUsers(Long id, String name, String login, String email, Integer month, String emailProvider) {
        StringBuilder query = new StringBuilder("SELECT u FROM User u WHERE 1 = 1");

        if (id != null) {
            query.append(" AND u.id = :id");
        }

        if (name != null) {
            query.append(" AND u.name LIKE :name");
        }

        if (login != null) {
            query.append(" AND u.login LIKE :login");
        }

        if (email != null) {
            query.append(" AND u.email LIKE :email");
        }

        if (emailProvider != null) {
            query.append(" AND SUBSTRING(u.email, LOCATE('@', u.email) + 1) = :emailProvider");
        }

        if (month != null) {
            query.append(" AND MONTH(u.birthDate) = :month");
        }

        TypedQuery<User> typedQuery = createQuery(query.toString());

        if (id != null) {
            typedQuery.setParameter("id", id);
        }

        if (name != null) {
            typedQuery.setParameter("name", "%" + name + "%");
        }

        if (login != null) {
            typedQuery.setParameter("login", "%" + login + "%");
        }

        if (email != null) {
            typedQuery.setParameter("email", "%" + email + "%");
        }

        if (emailProvider != null) {
            typedQuery.setParameter("emailProvider", emailProvider);
        }

        if (month != null) {
            typedQuery.setParameter("month", month);
        }

        return typedQuery.getResultList();
    }
}
