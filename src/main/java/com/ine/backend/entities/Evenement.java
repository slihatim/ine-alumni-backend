package com.ine.backend.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "evenements")
public class Evenement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titre;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime date;

	private String lieu;

	private String club;

	private String image;

	@Column(columnDefinition = "TEXT")
	private String schedule;

	@Column(columnDefinition = "TEXT")
	private String expectations;

	// Constructeurs
	public Evenement() {
	}

	public Evenement(String titre, String description, LocalDateTime date, String lieu, String club, String image,
			String schedule, String expectations) {
		this.titre = titre;
		this.description = description;
		this.date = date;
		this.lieu = lieu;
		this.club = club;
		this.image = image;
		this.schedule = schedule;
		this.expectations = expectations;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getExpectations() {
		return expectations;
	}

	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}
}
