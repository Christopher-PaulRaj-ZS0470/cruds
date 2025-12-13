package com.audius.music.cruds.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(schema = "user_management", name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    private String password;
    private String mail;
    @Column(name = "created_on")
    private Timestamp createdOn;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "phone_number")
    private Long phoneNumber;
    @Column(name = "dob")
    private LocalDate dob;
}
