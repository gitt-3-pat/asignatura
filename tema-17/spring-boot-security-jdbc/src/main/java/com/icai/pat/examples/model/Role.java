package com.icai.pat.examples.model;


import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import lombok.Data;

@Data
public class Role {

	
    @Id
    private Long id;
    private String name;
    @MappedCollection(idColumn = "ROLE_ID")
    private Set<PrivilegeRef> privileges = new HashSet<>();
    
    public void addPrivilege(Privilege privilege) {
    	privileges.add(new PrivilegeRef(privilege.getId()));
      }
    
    
	public Role() {
		super();
	}
	
	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
    
	
	
	@Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + "]";
    }

	public Role(String name) {
		super();
		this.name = name;
	}
    
    
}
