package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotNull(message = "name is required")
    @Size(min = 3, message = "name character should be more then 3")
    private String f_name;
    @NotNull(message = "phone is required")
    private String phone;
    @Email(message = "enter valid email")
    private  String email;
    @NotNull(message = "password is required")
    @Size(min = 5, message = "password character should be more then 5")
    private String password;

}
