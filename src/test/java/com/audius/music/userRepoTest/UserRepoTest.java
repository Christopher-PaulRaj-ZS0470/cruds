package com.audius.music.userRepoTest;

import com.audius.music.cruds.entity.User;
import com.audius.music.cruds.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;
    private User user;

    @Test
    void userRepoTest() {
        Optional<User> userReturn = userRepo.findByUserName("christopher");
        Optional<User> findByAccountName = userRepo.findByAccountName("sjc");
        Optional<User> userIdReturn = userRepo.findByUserId(1L);
        System.out.println(userIdReturn);
    }
}
