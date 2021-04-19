package com.icai.pat.examples.security;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.icai.pat.examples.dao.persistence.RoleRepository;
import com.icai.pat.examples.dao.persistence.UserRepository;
import com.icai.pat.examples.model.Role;
import com.icai.pat.examples.model.User;

import org.springframework.security.core.GrantedAuthority;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
	
	public CustomAuthenticationProvider() {
        super();
    }
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
 
        String name = authentication.getName();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());
        try {
        	final UserDetails principal = this.loadUserByUsername(name);
        	        	
        	final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
        	return auth;
        }
        catch (Exception ex) {
        	throw new BadCredentialsException("Username/Password does not match for " + name);
        }
       
    
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
       

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            
            List<Long> rolesIdList = new ArrayList<Long>();
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            
            user.getRoles().stream().forEach((c) -> rolesIdList.add(c.getRoleId()));
            
            final List<Role> roles=(List<Role>) roleRepository.findAllById(rolesIdList);
            
            for (int i=0;i<roles.size();i++)
            	 grantedAuths.add(new SimpleGrantedAuthority(roles.get(i).getName()));
          
          return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, grantedAuths);
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}