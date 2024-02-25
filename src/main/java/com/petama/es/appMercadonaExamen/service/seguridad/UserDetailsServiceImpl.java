package com.petama.es.appMercadonaExamen.service.seguridad;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

import org.springframework.beans.factory.annotation.Autowired;

/* Spring Security	*/

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petama.es.appMercadonaExamen.entity.seguridad.UsuarioEntity;
import com.petama.es.appMercadonaExamen.repository.seguridad.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsuarioEntity usuarioEntity= this.usuarioRepository.findByUsuario(username);
		
		if (isNull(usuarioEntity)) {
			throw new UsernameNotFoundException("Usuario no existe");
		}
		
		//return new UserDetailsImpl(usuarioEntity);
		
		return new User(usuarioEntity.getUsuario(),usuarioEntity.getClave(),emptyList());
	
	} 

}
