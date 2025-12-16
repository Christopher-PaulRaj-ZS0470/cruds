package com.audius.music.cruds.repo;

import com.audius.music.cruds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional <User> findByUserId(Long id);
    Optional<User> findByUserName(String userName);
    Optional<User> findByAccountName(String accountName);


}
