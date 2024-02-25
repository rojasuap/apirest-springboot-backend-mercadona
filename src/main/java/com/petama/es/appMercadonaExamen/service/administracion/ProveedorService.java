package com.petama.es.appMercadonaExamen.service.administracion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.service.generico.GenericoService;

public interface ProveedorService extends GenericoService<ProveedorDto> {

    Page<ProveedorDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException;

}
