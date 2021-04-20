package com.icai.pat.examples.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.icai.pat.examples.security.CustomAuthenticationProvider;


@Configuration
@EnableWebSecurity
@ComponentScan("com.icai.pat.examples.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    
	@Autowired
    private CustomAuthenticationProvider authProvider;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/h2/**").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/login", "/index.html").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/index-role-admin.html").hasRole("ADMIN")
			.and()
			.formLogin()
			.defaultSuccessUrl("/api/whoami")
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and().httpBasic();
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
	}
	


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password(passwordEncoder.encode("admin"))
				.roles("ADMIN");
		auth.
	}
*/
	/*
	@Autowired
	public void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .withDefaultSchema()
	      .withUser(User.withUsername("user")
	        .password(passwordEncoder.encode("pass"))
	        .roles("USER"));
	}
	*/
}
