package com.petama.es.appMercadonaExamen.service.seguridad;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.petama.es.appMercadonaExamen.entity.seguridad.UsuarioEntity;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private UsuarioEntity usuarioEntity;

	public UserDetailsImpl(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuarioEntity.getAuthorities().stream().map(
        			authority -> new SimpleGrantedAuthority(authority.getNombre())
        		).collect(Collectors.toList());
    }
    
	@Override
	public String getPassword() {
		return usuarioEntity.getClave();
	}

	@Override

	public String getUsername() {
		return usuarioEntity.getUsuario();
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

	public User getUserDetails() {
		return new User(usuarioEntity.getUsuario(),usuarioEntity.getClave(), this.getAuthorities());
	}
}
