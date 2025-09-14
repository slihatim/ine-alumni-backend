package com.ine.backend.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

	// events permissions
	EVENTS_READ("events:read"), EVENTS_CREATE("events:create"), EVENTS_UPDATE("events:update"), EVENTS_UPDATE_SELF(
			"events:update:self"), EVENTS_DELETE("events:delete"), EVENTS_DELETE_SELF("events:delete:self"),

	// resources permissions
	RESOURCES_READ("resources:read"), RESOURCES_CREATE("resources:create"), RESOURCES_UPDATE(
			"resources:update"), RESOURCES_UPDATE_SELF("resources:update:self"), RESOURCES_DELETE(
					"resources:delete"), RESOURCES_DELETE_SELF("resources:delete:self");

	@Getter
	private final String permission;
}
