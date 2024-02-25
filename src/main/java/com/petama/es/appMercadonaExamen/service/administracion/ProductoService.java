package com.petama.es.appMercadonaExamen.service.administracion;

import com.petama.es.appMercadonaExamen.dto.administracion.ProductoDto;
import com.petama.es.appMercadonaExamen.entity.administracion.ProductoEntity;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.service.generico.GenericoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductoService extends GenericoService<ProductoDto> {

    Page<ProductoDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException;

	Optional<ProductoDto> findByCodigoEan(String codEan) throws ServiceException;

	boolean buscarCodigoEan(String codEan) throws ServiceException;
	
	ProductoDto generateCodigoEan(ProductoDto productoDto) throws ServiceException;


}
