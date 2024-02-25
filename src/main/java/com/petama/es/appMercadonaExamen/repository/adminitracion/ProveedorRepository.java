package com.petama.es.appMercadonaExamen.repository.adminitracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity,Long> {

    @Query("select p from ProveedorEntity p where p.estado='1'")
    List<ProveedorEntity> findAllCustom();
    
    @Query("select p from ProveedorEntity p where upper(p.razonSocial) like upper(:razonSocial) and p.estado='1'")
    List<ProveedorEntity> findByLikeRazonSocial(@Param("razonSocial") String razonSocial);
    
    @Modifying
	@Query(nativeQuery=true, value= "UPDATE TBL_PROVEEDOR SET estado='0' where proveedor_id=:id") // SQL Nativo
	void delete(@Param("id") Long id);
}
