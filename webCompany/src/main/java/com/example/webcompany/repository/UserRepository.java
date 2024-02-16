package com.example.webcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.webcompany.entites.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.password = ?2")
    User authtification(String email, String password);

    @Modifying
    @Query("UPDATE User u SET u.firsttime = ?1 WHERE u.email = ?2")
    int updateIsFirstTime(boolean isFirstTime, String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    int updatePassword(@Param("email") String email, @Param("password") String password);

}
