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
@Table(name = "user")
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

	private String unternehmensregisternr;

	private String beschreibung;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_besitzt_abschluss",
			joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)},
			inverseJoinColumns = {
			@JoinColumn(name = "abschluss_id", referencedColumnName = "id", nullable = false, updatable = false)})
	private List<AbschlussEntity> userAbschluesse;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_besitzt_kompetenz",
			joinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)},
			inverseJoinColumns = {
					@JoinColumn(name = "kompetenz_id", referencedColumnName = "id", nullable = false, updatable = false)})
	private List<KompetenzenEntity> userKompetenzen ;
}