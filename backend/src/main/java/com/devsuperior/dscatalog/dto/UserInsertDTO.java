package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.User;

public class UserInsertDTO extends UserDTO{

	public UserInsertDTO(Long id, String firstName, 
			String lastName, String email, String password) {
		super(id, firstName, lastName, email, password);
	}
	
	public UserInsertDTO(User entity) {
		super(entity);
	}

	public UserInsertDTO() {
		super();
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
