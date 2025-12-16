package com.audius.music.userRepoTest;

import com.audius.music.cruds.entity.UserAccess;
import com.audius.music.cruds.repo.UserAccessRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserAccessRepoTest {

    @Autowired
    private UserAccessRepo userRepo;
    private UserAccess user;

    @Test
    void userAccessRepoTest() {
        Optional<UserAccess> userReturn = userRepo.findByUserAccessId(1001L);
        Optional<UserAccess> findByUserId = userRepo.findByUserId(1L);
        Optional<UserAccess> userIdReturn = userRepo.findByPhoneNumber("9876543210");
        System.out.println(userIdReturn);
    }
}
