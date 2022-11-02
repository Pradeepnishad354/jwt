	package com.security.jwt.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
@Id
@GeneratedValue
private int id;
private String name;
private String username;
private String password;

@ElementCollection
@CollectionTable(name="rolestable",joinColumns = @JoinColumn(name="id"))
@Column(name="roles")
private Set<String> roles;


}
