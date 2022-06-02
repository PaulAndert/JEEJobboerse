package de.thb.jee.authexample.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Builder
@ToString
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String email;

	private String password;
	
	private boolean enabled;

	private int roleId;

	private String vorname;

	private String nachname;

	private String adresse;

	private String unternehmensname;

	private int unternehmensregisternr;

	private String beschreibung;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable
	private List<AbschlussEntity> Abschluesse;
}