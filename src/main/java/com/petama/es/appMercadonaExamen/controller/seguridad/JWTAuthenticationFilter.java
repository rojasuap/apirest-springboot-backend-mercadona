package com.petama.es.appMercadonaExamen.controller.seguridad;


import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petama.es.appMercadonaExamen.entity.seguridad.UsuarioEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/* login */

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			UsuarioEntity usuarioEntity = new ObjectMapper().readValue(request.getInputStream(), UsuarioEntity.class);

			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(usuarioEntity.getUsuario(), usuarioEntity.getClave());

			Authentication aut = authenticationManager.authenticate(upat);

			return aut;

		} catch (IOException e) {
			System.out.println("attemptAuthentication " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		byte signingKey[] = Constants.SUPER_SECRET_KEY.getBytes();

		
		
		UserDetails userDetails=(UserDetails) auth.getPrincipal();
				
		 Collection<? extends GrantedAuthority> authorities=userDetails.getAuthorities();

		Collection<?> authoritiesItems= authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()); 
		
		String token = Jwts.builder()
				.setIssuedAt(new Date())
				.setIssuer(Constants.ISSUER_INFO)
				.setSubject(userDetails.getUsername())
				.claim(Constants.AUTHORITIES, authoritiesItems)
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, signingKey)
				.compact();

		response.addHeader("Access-Control-Expose-Headers", "Authorization");

		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);

	}

}
