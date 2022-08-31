package com.safara.security.entities;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Getter
@Setter
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

	@NaturalId(mutable=true)
    @NotBlank
    @Size(max = 50)
    @Email
	private String email;

	private String tel;
	
	@JsonIgnore
	@NotBlank
	@Size(min=6, max = 100)
	private  String password;

	@JsonIgnore
	@Column(name="token")
	private String token;

	@JsonIgnore
	@Column(name="expire")
	private boolean expire;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="user_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns= @JoinColumn(name="role_id"))
	private Set<RoleUser> roles = new HashSet<>();
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updated;

	
}
