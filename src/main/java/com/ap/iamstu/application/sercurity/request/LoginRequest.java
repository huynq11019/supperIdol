package com.ap.iamstu.application.sercurity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {
	@NotNull
	private String username;

	@NotNull
	private String password;

	private Boolean rememberMe;

	@Override
	public String toString() {
		return "LoginRequest{" + "username='" + username + '\'' + ", rememberMe=" + rememberMe + '}';
	}
}
