package com.petama.es.appMercadonaExamen.repository.adminitracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petama.es.appMercadonaExamen.entity.administracion.DestinoEntity;

@Repository
public interface DestinoRepository extends JpaRepository<DestinoEntity,Long> {

    @Query("select p from DestinoEntity p where p.estado='1'")
    List<DestinoEntity> findAllCustom();
    
    @Query("select p from DestinoEntity p where upper(p.nombre) like upper(:nombre) and p.estado='1'")
    List<DestinoEntity> findByLikeNombre(@Param("nombre") String nombre);
    
    @Modifying
	@Query(nativeQuery=true, value= "UPDATE TBL_DESTINO SET estado='0' where destino_id=:id") // SQL Nativo
	void delete(@Param("id") Long id);
}

