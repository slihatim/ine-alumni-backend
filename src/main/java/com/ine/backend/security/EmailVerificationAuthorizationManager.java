package com.ine.backend.security;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.ine.backend.services.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmailVerificationAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	private UserService userService;

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		Authentication auth = authentication.get();
		if (auth != null && auth.isAuthenticated()) {
			String requestPath = context.getRequest().getRequestURI();
			String username = auth.getName();

			boolean isEmailVerified = userService.isEmailVerified(username);
			if (!isEmailVerified) {
				// allow unverified authenticated users to only access verification endpoints
				return new AuthorizationDecision(requestPath.startsWith("/api/v1/email"));
			} else {
				// Prevent verified users from accessing verification endpoints
				return new AuthorizationDecision(!requestPath.startsWith("/api/v1/email"));
			}
		}
		return new AuthorizationDecision(false);
	}
}
