package com.petama.es.appMercadonaExamen.service.generico;

import java.util.List;
import java.util.Optional;

import com.petama.es.appMercadonaExamen.service.exception.ServiceException;

public interface GenericoService<T> {

    List<T> findAll () throws ServiceException;
    
    List<T> findAllActive () throws ServiceException;

    List<T> findByLikeObject(T t) throws ServiceException;

    Optional<T> findById(Long id) throws ServiceException;

    T save(T t) throws ServiceException;

    T update(T t) throws ServiceException;

    Boolean delete(T t) throws ServiceException;
}
