package com.serg.config.auth;

import com.serg.model.Credentials;
import com.serg.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static java.lang.String.valueOf;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("/api/session")
public class AuthenticationResource {
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthenticationResource(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@RequestMapping(method = POST)
	public LoginUser login(@RequestBody Credentials credentials, HttpSession httpSession) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
		getContext().setAuthentication(authenticationManager.authenticate(authentication));

		LoginUser loginUser = new LoginUser(credentials.getUsername(), httpSession.getId(), true);
		httpSession.setAttribute(getAttributeSessionName(), loginUser);
		return loginUser;
	}

	@RequestMapping(method = GET)
	public Object session(HttpSession session) {
		String className = valueOf(LoginUser.class.getSimpleName());
		className = className.substring(0, 1).toLowerCase() + className.substring(1);
		return session.getAttribute(getAttributeSessionName());
	}

	@RequestMapping(method = DELETE)
	public void logout(HttpSession session) {
		session.invalidate();
	}

	private String getAttributeSessionName() {
		String className = valueOf(LoginUser.class.getSimpleName());
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}
}
