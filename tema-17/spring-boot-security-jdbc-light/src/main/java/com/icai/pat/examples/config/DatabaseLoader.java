package com.icai.pat.examples.config;


import com.icai.pat.examples.dao.persistence.UserRepository;
import com.icai.pat.examples.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

	
    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
   
        // == create initial user
        createUserIfNotFound("admin@test.com", "Test", "Test", "test","ROLE_ADMIN");
        createUserIfNotFound("user@test.com", "Test", "Test", "test", "ROLE_USER");

        alreadySetup = true;
    }




    @Transactional
    User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, String role) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRole(role);
        }
                
        user = userRepository.save(user);
        return user;
    }

    @Bean
    public PasswordEncoder encoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
       
    }
    
    
}