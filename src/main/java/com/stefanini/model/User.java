package com.stefanini.model;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.security.PasswordUtils;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Entity
@Data
@Table(name = "USUARIO")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "NOME", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 5, max = 20)
    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @NotBlank
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

    @NotBlank
    @Column(name = "SALT", nullable = false)
    private String salt;

    @NotBlank
    @Column(name = "SENHA", nullable = false)
    private String password;

    public User(UserCreationDTO userCreationDTO) {
        this.name = userCreationDTO.getName();
        this.login = userCreationDTO.getLogin();
        this.salt = PasswordUtils.generateSalt();
        this.password = PasswordUtils.hashPassword(userCreationDTO.getPassword(), this.salt);
        this.email = userCreationDTO.getEmail();
        this.birthDate = userCreationDTO.getBirthDate();
        this.creationTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    public User() {}
}
