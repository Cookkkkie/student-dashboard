package com.example.main.repository;

import com.example.main.modals.UserMod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserMod, Long> {
    Optional<UserMod> findByEmail(String email);
    List<UserMod> findAllByOrderByUserID();
    Optional<UserMod> findByUserID(long userID);

}