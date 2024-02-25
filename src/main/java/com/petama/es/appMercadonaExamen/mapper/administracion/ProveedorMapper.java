package com.petama.es.appMercadonaExamen.mapper.administracion;

import org.mapstruct.Mapper;

import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;

@Mapper(componentModel = "spring")

public interface ProveedorMapper {
    ProveedorEntity toEntity(ProveedorDto proveedorDTO);
    ProveedorDto toDTO(ProveedorEntity proveedorEntity);

}
