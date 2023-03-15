package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.validation.ValidPassword;

public class UserDTO {
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public UserDTO() {
    	
    }
    
    public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fullname = user.getFullname();
		this.role = user.getRole();
	}
    
    public UserDTO(String username, String password, String fullname, String role) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		UserDTO other = (UserDTO) obj;
		return Objects.equals(fullname, other.fullname) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(username, other.username);
	}
}
