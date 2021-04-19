package com.icai.pat.examples.model;


import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class Privilege {

	
	@Id
    private Long id;
    private String name;


	public Privilege() {
		super();
	}
	
	
	
	public Privilege(Long  id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
    
	@Override
    public String toString() {
        return "Privilege [id=" + id + ", name=" + name + "]";
    }



	public Privilege(String name) {
		super();
		this.name = name;
	}
    

}
