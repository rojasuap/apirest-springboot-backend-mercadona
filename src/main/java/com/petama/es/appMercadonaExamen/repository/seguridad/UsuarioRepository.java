package com.petama.es.appMercadonaExamen.repository.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petama.es.appMercadonaExamen.entity.seguridad.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	UsuarioEntity findByUsuario(String usuario);
		
}
