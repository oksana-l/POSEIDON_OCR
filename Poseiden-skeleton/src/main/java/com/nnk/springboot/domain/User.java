package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.nnk.springboot.domain.dto.UserDTO;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column
    private String username;
    @NotBlank
    @Column
    private String password;
    @NotBlank
    @Column
    private String fullname;
    @NotBlank
    @Column
    private String role;

    public User() {
    	
    }
    
    public User(Integer id, String username, String password, String fullname, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	public User(UserDTO userDto) {
		this.username = userDto.getUsername();
		this.password = userDto.getPassword();
		this.fullname = userDto.getFullname();
		this.role = userDto.getRole();
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
}
