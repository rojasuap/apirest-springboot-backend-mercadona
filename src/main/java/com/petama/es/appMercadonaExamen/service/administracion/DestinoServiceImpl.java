package com.petama.es.appMercadonaExamen.service.administracion;

import static com.petama.es.appMercadonaExamen.common.GlobalConstant.ESTADO_DESTINO_ACTIVO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.petama.es.appMercadonaExamen.dto.administracion.DestinoDto;
import com.petama.es.appMercadonaExamen.entity.administracion.DestinoEntity;
import com.petama.es.appMercadonaExamen.mapper.administracion.DestinoMapper;
import com.petama.es.appMercadonaExamen.repository.adminitracion.DestinoRepository;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.util.BDUtil;

import lombok.extern.slf4j.Slf4j;;

@Slf4j
@Service
public class DestinoServiceImpl implements DestinoService {

    private final DestinoRepository destinoRepository;
    private final DestinoMapper destinoMapper;


    public DestinoServiceImpl(final DestinoRepository destinoRepository, DestinoMapper destinoMapper) {
        super();
    	this.destinoRepository=destinoRepository;
        this.destinoMapper=destinoMapper;
    }

    @Cacheable("findAllDestinos")
    @Override
    public List<DestinoDto> findAll() throws ServiceException {
        try {
            List<DestinoEntity> lst = destinoRepository.findAll();
            return lst.stream().map(e-> destinoMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public List<DestinoDto> findAllActive() throws ServiceException {
        try {
            List<DestinoEntity> lst = destinoRepository.findAllCustom();
            return lst.stream().map(e-> destinoMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Page<DestinoDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException {
        return null;
    }

    @Override
    public List<DestinoDto> findByLikeObject(DestinoDto destinoDto) throws ServiceException {
        try {
            List<DestinoEntity> lst= destinoRepository.findByLikeNombre(BDUtil.getLike(destinoDto.getNombre()));
            return lst.stream().map(e-> destinoMapper.toDTO(e)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<DestinoDto> findById(Long id) throws ServiceException {
        
        try {
        	Optional<DestinoEntity> optdestinoEntity = destinoRepository.findById(id);
        	if(optdestinoEntity==null) {
        		return Optional.empty();	
        	}
            return  Optional.of(destinoMapper.toDTO(optdestinoEntity.get()));	
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public DestinoDto save(DestinoDto destinoDto) throws ServiceException {
    	try {
    		List<DestinoDto> destinos = this.findByLikeObject(destinoDto);
    		if (destinos.isEmpty()) {
    			destinoDto.setEstado(ESTADO_DESTINO_ACTIVO);
    			return destinoMapper.toDTO(destinoRepository.save(destinoMapper.toEntity(destinoDto)));	
    		}
    		return null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public DestinoDto update(DestinoDto destinoDto) throws ServiceException {
    	try {
    		DestinoDto optdestino = this.findById(destinoDto.getId()).orElse(null);
			if (!Objects.isNull(optdestino)) {
    			destinoDto.setEstado(ESTADO_DESTINO_ACTIVO);
				BeanUtils.copyProperties(destinoDto,optdestino);
				return destinoMapper.toDTO(destinoRepository.save(destinoMapper.toEntity(optdestino)));
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }

    @Transactional
    @Override
    public Boolean delete(DestinoDto destinoDto) throws ServiceException {
    	try {
			destinoRepository.delete(destinoDto.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }
    
}
