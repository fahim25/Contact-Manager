package com.contactmanager.repository;

import com.contactmanager.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository <Contact, Integer> {

    //pagination




    //custom find


    @Query("from Contact as c where c.user.uId =:userId")
    public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);


}
