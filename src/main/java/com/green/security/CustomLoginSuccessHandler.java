package com.green.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		log.warn("로그인 성공" );
		List<String> roleName = new ArrayList<String>();
		authentication.getAuthorities().forEach(i -> {
			roleName.add(i.getAuthority());
		});
		log.warn("RoleName : " +roleName);
		if(roleName.contains("ROLE_MEMBER")){
			response.sendRedirect("/sample/member");
			return;
		}
		if(roleName.contains("ROLE_ADMIN")){
			response.sendRedirect("/sample/admin");
			return;
		}
		response.sendRedirect("/");
		
	}
}
