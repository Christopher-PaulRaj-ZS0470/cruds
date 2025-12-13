package com.audius.music.cruds.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(schema = "user_management", name = "user_access")
public class UserAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_access_id")
    private Long userAccessId;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "created_on")
    private Timestamp createdOn;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "phone_number")
    private Long phoneNumber;
}
