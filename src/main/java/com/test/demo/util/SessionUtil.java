package com.test.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.test.demo.entity.User;

@Component
public class SessionUtil {
	
	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null)
			return (User) authentication.getPrincipal();
		else {
			String msg = "Failure when getting logged in user data.";
			throw new RuntimeException(msg);
		}
	}

}
