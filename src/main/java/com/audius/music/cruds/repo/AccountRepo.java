package com.audius.music.cruds.repo;

import com.audius.music.cruds.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional <Account> findByAccountId(Long accountId);
    Optional<Account> findByAccountName(String accountName);
    Optional <Account> findByUserId(Long id);
    Optional<Account> findByPhoneNumber(String phoneNumber);


}
