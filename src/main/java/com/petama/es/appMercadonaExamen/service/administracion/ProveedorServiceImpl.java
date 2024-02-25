package com.petama.es.appMercadonaExamen.service.administracion;

import static com.petama.es.appMercadonaExamen.common.GlobalConstant.ESTADO_PROVEEDOR_ACTIVO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;
import com.petama.es.appMercadonaExamen.mapper.administracion.ProveedorMapper;
import com.petama.es.appMercadonaExamen.repository.adminitracion.ProveedorRepository;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.util.BDUtil;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;;

@Slf4j
@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;


    public ProveedorServiceImpl(final ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        super();
    	this.proveedorRepository=proveedorRepository;
        this.proveedorMapper=proveedorMapper;
    }

    @Override
    public List<ProveedorDto> findAll() throws ServiceException {
        try {
            List<ProveedorEntity> lst = proveedorRepository.findAll();
            return lst.stream().map(e-> proveedorMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public List<ProveedorDto> findAllActive() throws ServiceException {
        try {
            List<ProveedorEntity> lst = proveedorRepository.findAllCustom();
            return lst.stream().map(e-> proveedorMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Page<ProveedorDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException {
        return null;
    }

    @Override
    public List<ProveedorDto> findByLikeObject(ProveedorDto proveedorDto) throws ServiceException {
        try {
            List<ProveedorEntity> lst= proveedorRepository.findByLikeRazonSocial(BDUtil.getLike(proveedorDto.getRazonSocial()));
            return lst.stream().map(e-> proveedorMapper.toDTO(e)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<ProveedorDto> findById(Long id) throws ServiceException {
        
        try {
        	Optional<ProveedorEntity> optProveedorEntity = proveedorRepository.findById(id);
        	if(optProveedorEntity==null) {
        		return Optional.empty();	
        	}
            return  Optional.of(proveedorMapper.toDTO(optProveedorEntity.get()));	
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ProveedorDto save(ProveedorDto proveedorDto) throws ServiceException {
    	try {
    		List<ProveedorDto> proveedors = this.findByLikeObject(proveedorDto);
    		if (proveedors.isEmpty()) {
    			proveedorDto.setEstado(ESTADO_PROVEEDOR_ACTIVO);
    			return proveedorMapper.toDTO(proveedorRepository.save(proveedorMapper.toEntity(proveedorDto)));	
    		}
    		return null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ProveedorDto update(ProveedorDto proveedorDto) throws ServiceException {
    	try {
    		ProveedorDto optProveedor = this.findById(proveedorDto.getIdProveedor()).orElse(null);
			if (!Objects.isNull(optProveedor)) {
    			proveedorDto.setEstado(ESTADO_PROVEEDOR_ACTIVO);
				BeanUtils.copyProperties(proveedorDto,optProveedor);
				return proveedorMapper.toDTO(proveedorRepository.save(proveedorMapper.toEntity(optProveedor)));
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }

    @Transactional
    @Override
    public Boolean delete(ProveedorDto proveedorDto) throws ServiceException {
    	try {
			proveedorRepository.delete(proveedorDto.getIdProveedor());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }
    
}
