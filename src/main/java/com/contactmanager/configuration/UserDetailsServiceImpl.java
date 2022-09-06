package com.contactmanager.configuration;

import com.contactmanager.model.User;
import com.contactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        //fetching data from database
        User user = userRepository.getUserByUserName(username);

        if (user==null){
            throw new UsernameNotFoundException("User Name Didn't match!");
        }

        CustomerUserDetails customUserDetails = new CustomerUserDetails(user);


        return customUserDetails;
    }
}
