package com.stefanini.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserCreationDTO {

    public static UserCreationDTO userCreationDTOExample = new UserCreationDTO(
            "John Doe", "johndoe31", "Password2@", "john.doe@gmail.com", LocalDate.of(1985, 10, 31));

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 20)
    private String login;

    @NotEmpty
    @Column(name = "SENHA")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,10}$",
            message = "Senha deve conter de 4 a 10 caracteres, 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial")
    private String password;

    @NotEmpty
    @Size(min = 10)
    @Email(message = "Formato inválido")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    public UserCreationDTO(String name, String login, String password, String email, LocalDate birthDate) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }
}
