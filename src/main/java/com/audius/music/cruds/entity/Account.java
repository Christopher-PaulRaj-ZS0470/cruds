package com.audius.music.cruds.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(schema = "user_management", name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "created_by")
    private Timestamp createdBy;
    @Column(name = "created_on")
    private Timestamp createdOn;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "phone_number")
    private LocalDate phoneNumber;
}
