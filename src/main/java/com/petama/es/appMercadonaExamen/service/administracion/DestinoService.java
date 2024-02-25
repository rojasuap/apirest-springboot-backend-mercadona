package com.petama.es.appMercadonaExamen.service.administracion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.petama.es.appMercadonaExamen.dto.administracion.DestinoDto;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.service.generico.GenericoService;

public interface DestinoService extends GenericoService<DestinoDto> {

    Page<DestinoDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException;

}
