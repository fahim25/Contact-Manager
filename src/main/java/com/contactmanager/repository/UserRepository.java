package com.contactmanager.repository;

import com.contactmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("select u from User u where u.uEmail = :uEmail")
    public User getUserByUserName(@Param("uEmail") String uEmail);

}
