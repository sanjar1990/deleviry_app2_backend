package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "name")
    private String f_name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private  String email;
    @Column(name = "password")
    private String password;
    @Column(name = "visible")
    private boolean visible=true;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;
}
