package com.petama.es.appMercadonaExamen.mapper.administracion;

import com.petama.es.appMercadonaExamen.dto.administracion.ProductoDto;
import com.petama.es.appMercadonaExamen.entity.administracion.ProductoEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ProductoMapper {
    ProductoEntity toEntity(ProductoDto productoDTO);
    ProductoDto toDTO(ProductoEntity productoEntity);

}
