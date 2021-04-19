package com.icai.pat.examples.model;


import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import lombok.Data;

@Data
public class User {

	
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    
    @MappedCollection(idColumn = "USER_ID")
    private Set<RoleRef> roles = new HashSet<>();
    
    public void addRole(Role role){
    	roles.add(new RoleRef(role.getId()));
      }
   

    public User(Long id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		
	}

	public User() {
		super();
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password+"]";
    }
	
}
