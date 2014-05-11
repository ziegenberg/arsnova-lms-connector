package de.thm.arsnova.connector.core;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpClientErrorException;

import de.thm.arsnova.connector.dao.ConnectorDao;
import de.thm.arsnova.connector.services.InternalUserService;

public class RepoPermissionEvaluator implements PermissionEvaluator {

	@Value("${service.studipclient.url}") private String url;
	@Value("${service.studipclient.username}") private String username;
	@Value("${service.studipclient.password}") private String password;

	@Autowired
	ConnectorDao dao;

	@Autowired
	InternalUserService internalUserService;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return false;
	}

	/** Checks permission
	 *
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		if (authentication.getPrincipal() instanceof String) return false;

		UserDetails ud = (UserDetails)authentication.getPrincipal();

		if (isAdmin(ud)) return true;

		try {
			switch (targetType) {
			case "membership":
			case "courses":
				if ("read".equals(permission) && ud.getUsername().equals(targetId) ) {
					return true;
				}
				break;

			case "uniRepQuestion":
			case "uniRepTree":
				if ("read".equals(permission)) {
					return true;
				}
				break;
			}
		} catch (HttpClientErrorException e) {
			return false;
		}
		return false;
	}

	private boolean isAdmin(UserDetails user) {
		return internalUserService.getUser(user.getUsername()).isAdmin();
	}
}
