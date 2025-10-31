package com.ine.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Skill entity to store alumni skills
 */
@Entity
@Table(name = "skills")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Skill name is required")
	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private SkillCategory category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "laureat_id", nullable = false)
	private Laureat laureat;
}
