package info.jab.microservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if ("user".equals(username)) {
			return new User("user",
					passwordEncoder.encode("password"),//"$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					List.of((GrantedAuthority) () -> "USER"));
		} else if ("admin".equals(username)) {
			return new User("admin",
					passwordEncoder.encode("password"),
					List.of((GrantedAuthority) () -> "ADMIN"));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}