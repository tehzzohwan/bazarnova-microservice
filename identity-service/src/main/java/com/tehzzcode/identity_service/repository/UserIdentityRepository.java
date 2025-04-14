package com.tehzzcode.identity_service.repository;

import com.tehzzcode.identity_service.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
    Optional<UserIdentity> findByEmail(String email);
}
