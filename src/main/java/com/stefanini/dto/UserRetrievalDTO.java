package com.stefanini.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.stefanini.model.User;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserRetrievalDTO {
    private String name;
    private String login;
    private String email;
    private LocalDate birthDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;

    public UserRetrievalDTO(User user) {
        this.name = user.getName();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.creationTime = user.getCreationTime();
        this.updateTime = user.getUpdateTime();
    }
}
