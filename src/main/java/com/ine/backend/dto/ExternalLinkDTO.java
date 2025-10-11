package com.ine.backend.dto;

import com.ine.backend.entities.LinkType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExternalLinkDTO {
	private Long id;
	private String title;
	private String url;
	private LinkType linkType;
}
