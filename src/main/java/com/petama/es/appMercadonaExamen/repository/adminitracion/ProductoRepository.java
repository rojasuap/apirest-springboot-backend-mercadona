package com.petama.es.appMercadonaExamen.repository.adminitracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petama.es.appMercadonaExamen.entity.administracion.ProductoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {

    @Query("select p from ProductoEntity p where p.estado='1'")
    List<ProductoEntity> findAllCustom();
    
    @Query("select p from ProductoEntity p where upper(p.nombre) like upper(:nombre) and p.estado='1'")
    List<ProductoEntity> findByLikeNombre(@Param("nombre") String nombre);
    
    @Query("select p from ProductoEntity p where p.codEan =:codEan and p.estado='1'")
	Optional<ProductoEntity> findByCodEan(@Param("codEan") String codEan);
    
    @Modifying
	@Query(nativeQuery=true, value= "UPDATE TBL_PRODUCTO SET estado='0' where producto_id=:id") // SQL Nativo
	void delete(@Param("id") Long id);
    
    @Modifying
	@Query(nativeQuery=true, value= "UPDATE TBL_PRODUCTO SET codigo_aen=:codigoAen where producto_id=:id") // SQL Nativo
	void generateCodigoAen(@Param("codigoAen") String codigoAen,@Param("id") long id);
}
