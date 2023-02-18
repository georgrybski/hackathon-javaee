package com.stefanini.model;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.utils.PasswordUtils;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USUARIO")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "NOME", nullable = false)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 20)
    @Column(name = "LOGIN", unique = true)
    private String login;

    @NotEmpty
    @Size(min = 10)
    @Email(message = "Formato inválido")
    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column(name = "DATA_DE_NASCIMENTO", nullable = false)
    private LocalDate birthDate;

    @Column(name = "DATA_DE_CRIACAO", nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "DATA_DE_ATUALIZACAO")
    private LocalDateTime updateTime;

    @Column(name = "SALT", nullable = false)
    private String salt;

    @Column(name = "SENHA", nullable = false)
    private String password;

    public User(UserCreationDTO userCreationDTO) {
        this.name = userCreationDTO.getName();
        this.login = userCreationDTO.getLogin();
        this.salt = PasswordUtils.generateSalt();
        this.password = PasswordUtils.hashPassword(userCreationDTO.getPassword(), this.salt);
        this.email = userCreationDTO.getEmail();
        this.birthDate = userCreationDTO.getBirthDate();
        this.creationTime = LocalDateTime.now();
    }

    public User() {}
}
