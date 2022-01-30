package com.disqo.notes.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.disqo.notes.model.User;
import com.disqo.notes.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@WebFilter(urlPatterns = "/Notes/*")
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final List<String> EXCLUDE_URLS = Arrays.asList("/token", "/health", "/actuator", "/swagger-ui",
			"/swagger-resources", "/api-docs");

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	UserRepository userRepository;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String url = request.getRequestURI().toString();
		for (String excludeUrl : EXCLUDE_URLS) {
			if (url.contains(excludeUrl)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String jwtToken = extractJwtFromRequest(request);
		if (!StringUtils.hasText(jwtToken)) {
			throw new ResourceNotFoundException("Token is Empty");
		}

		String email = getEmailToken(jwtToken);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("Invalid Token");
		}
		log.info("Token is valid for the user: {}", user.getEmail());
		AuthContext.setUserId(user.getUserId());

		chain.doFilter(request, response);
	}

	public String getEmailToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}