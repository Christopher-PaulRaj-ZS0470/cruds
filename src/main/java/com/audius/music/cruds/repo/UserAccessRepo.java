package com.audius.music.cruds.repo;

import com.audius.music.cruds.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepo extends JpaRepository<UserAccess, Long> {
    Optional <UserAccess> findByUserAccessId(Long id);
    Optional<UserAccess> findByUserId(Long userId);
    Optional<UserAccess> findByPhoneNumber(String phoneNumber);

}
