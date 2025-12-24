package com.wissen.dta.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	private final String code;
	private final Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String code, Collection<? extends GrantedAuthority> authorities) {
		this.code = code;
		this.authorities = authorities;
	}

	public boolean hasRole(String role) {
		return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return code;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
