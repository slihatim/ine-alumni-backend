package com.ine.backend.dto;

import com.ine.backend.entities.SkillCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SkillDTO {
	private Long id;
	private String name;
	private SkillCategory category;
}
