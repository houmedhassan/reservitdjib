package com.safara.security.entities;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="users", uniqueConstraints= {
		@UniqueConstraint(columnNames = {
				"username"
		}),
		@UniqueConstraint(columnNames = {
				"email"
		})
})
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=3, max=50)
	private String name;
	
	@NotBlank
	@Size(min=3, max=50)	
	private String username;
	
	@NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
	private String email;
	
	@JsonIgnore
	@NotBlank
	@Size(min=6, max = 100)
	private  String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns= @JoinColumn(name="role_id"))
	private Set<RoleUser> roles = new HashSet<>();
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updated;
	
	public User() {}
	 
    public User(String name, String username, String email, String password,  LocalDateTime created, LocalDateTime updated) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.created = created;
        this.updated = updated;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleUser> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleUser> roles) {
		this.roles = roles;
	}
    
	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
    
	
}
