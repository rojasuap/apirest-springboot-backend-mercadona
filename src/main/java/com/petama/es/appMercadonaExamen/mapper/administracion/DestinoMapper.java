package com.petama.es.appMercadonaExamen.mapper.administracion;

import org.mapstruct.Mapper;

import com.petama.es.appMercadonaExamen.dto.administracion.DestinoDto;
import com.petama.es.appMercadonaExamen.entity.administracion.DestinoEntity;

@Mapper(componentModel = "spring")

public interface DestinoMapper {
	DestinoEntity toEntity(DestinoDto destinoDTO);
	DestinoDto toDTO(DestinoEntity destinoEntity);

}
