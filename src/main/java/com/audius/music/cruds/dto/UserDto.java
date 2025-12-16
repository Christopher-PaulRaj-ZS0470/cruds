package com.audius.music.cruds.dto;


import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private String password;
    private String mail;
    private Timestamp createdOn;
    private String accountName;
    private String phoneNumber;
    private LocalDate dob;
}

