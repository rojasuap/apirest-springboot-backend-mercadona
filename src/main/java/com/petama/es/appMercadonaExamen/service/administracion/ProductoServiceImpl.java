package com.petama.es.appMercadonaExamen.service.administracion;

import static com.petama.es.appMercadonaExamen.common.GlobalConstant.ESTADO_PRODUCTO_ACTIVO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.petama.es.appMercadonaExamen.dto.administracion.DestinoDto;
import com.petama.es.appMercadonaExamen.dto.administracion.ProductoDto;
import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.entity.administracion.ProductoEntity;
import com.petama.es.appMercadonaExamen.mapper.administracion.DestinoMapper;
import com.petama.es.appMercadonaExamen.mapper.administracion.ProductoMapper;
import com.petama.es.appMercadonaExamen.mapper.administracion.ProveedorMapper;
import com.petama.es.appMercadonaExamen.repository.adminitracion.ProductoRepository;
import com.petama.es.appMercadonaExamen.service.exception.ServiceException;
import com.petama.es.appMercadonaExamen.util.BDUtil;

import lombok.extern.slf4j.Slf4j;;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProveedorMapper proveedorMapper;
    private final DestinoMapper destinoMapper;
    private final ProveedorService proveedorService;
    private final DestinoService destinoService;

    public ProductoServiceImpl(final ProductoRepository productoRepository, final ProductoMapper productoMapper,
    						   final ProveedorMapper proveedorMapper, final DestinoMapper destinoMapper,
    						   final ProveedorService proveedorService,final DestinoService destinoService) {
        super();
    	this.productoRepository=productoRepository;
        this.productoMapper=productoMapper;
        this.proveedorMapper=proveedorMapper;
        this.destinoMapper=destinoMapper;
        this.proveedorService=proveedorService;
        this.destinoService=destinoService;
    }

    @Override
    public List<ProductoDto> findAll() throws ServiceException {
        try {
            List<ProductoEntity> lst = productoRepository.findAll();
            return lst.stream().map(e-> productoMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public List<ProductoDto> findAllActive() throws ServiceException {
        try {
            List<ProductoEntity> lst = productoRepository.findAllCustom();
            return lst.stream().map(e-> productoMapper.toDTO(e)).collect(Collectors.toList());
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Page<ProductoDto> findByLikeNombrePaging(Pageable pageable, String nombre) throws ServiceException {
        return null;
    }

    @Override
    public List<ProductoDto> findByLikeObject(ProductoDto productoDto) throws ServiceException {
        try {
            List<ProductoEntity> lst= productoRepository.findByLikeNombre(BDUtil.getLike(productoDto.getNombre()));
            return lst.stream().map(e-> productoMapper.toDTO(e)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<ProductoDto> findById(Long id) throws ServiceException {
        
        try {
        	Optional<ProductoEntity> optProductoEntity = productoRepository.findById(id);
        	if(optProductoEntity==null) {
        		return Optional.empty();	
        	}
            return  Optional.of(productoMapper.toDTO(optProductoEntity.get()));	
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Optional<ProductoDto> findByCodigoEan(String codEan) throws ServiceException {
        
        try {
        	if (this.buscarCodigoEan(codEan)){
        		Optional<ProductoEntity> optProductoEntity = productoRepository.findByCodEan(codEan);
            	if(optProductoEntity==null) {
            		return Optional.empty();	
            	}
            	return  Optional.of(productoMapper.toDTO(optProductoEntity.get()));		
        	}
            else {
            	return Optional.empty();	
        	}            	
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductoDto save(ProductoDto productoDto) throws ServiceException {
    	try {
    		List<ProductoDto> productos = this.findByLikeObject(productoDto);
    		if (productos.isEmpty()) {
    			
    			Optional<ProveedorDto> optProveedor = proveedorService.findById(productoDto.getProveedor().getIdProveedor());
    	        if (optProveedor==null) {
    	        	throw new ServiceException("El Proveedor no existe");
    	        }
    	        productoDto.setProveedor(proveedorMapper.toEntity(optProveedor.get()));
    	        
    	        Optional<DestinoDto> optDestino = destinoService.findById(BDUtil.getDestinoId(Long.valueOf(productoDto.getTienda()).intValue()));
    	        if (optDestino==null) {
    	        	throw new ServiceException("El Destino no existe");
    	        } 
    	        productoDto.setDestino(destinoMapper.toEntity(optDestino.get()));
    			return productoMapper.toDTO(productoRepository.save(productoMapper.toEntity(productoDto)));	
    		}
    		return null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public ProductoDto generateCodigoEan(ProductoDto productoDto) throws ServiceException {
    	try {
    		ProductoDto optProducto = this.findById(productoDto.getId()).orElse(null);
			if (!Objects.isNull(optProducto)) {
				String strIdProveedor=BDUtil.getFormat(optProducto.getProveedor().getIdProveedor().toString(),7);
				String strIdProducto=BDUtil.getFormat(optProducto.getId().toString(),5);
				optProducto.setCodEan(strIdProveedor+strIdProducto+optProducto.getTienda());
				
				Optional<DestinoDto> optDestino = destinoService.findById(BDUtil.getDestinoId(Long.valueOf(productoDto.getTienda()).intValue()));
    	        if (optDestino==null) {
    	        	throw new ServiceException("El Destino no existe");
    	        }
    	        optProducto.setDestino(destinoMapper.toEntity(optDestino.get()));
				
				return productoMapper.toDTO(productoRepository.save(productoMapper.toEntity(optProducto)));
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }

    @Override
    public ProductoDto update(ProductoDto productoDto) throws ServiceException {
    	try {
    		ProductoDto optProducto = this.findById(productoDto.getId()).orElse(null);
			if (!Objects.isNull(optProducto)) {
    			productoDto.setEstado(ESTADO_PRODUCTO_ACTIVO);
				BeanUtils.copyProperties(productoDto,optProducto);
				return productoMapper.toDTO(productoRepository.save(productoMapper.toEntity(optProducto)));
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }

    @Transactional
    @Override
    public Boolean delete(ProductoDto productoDto) throws ServiceException {
    	try {
			productoRepository.delete(productoDto.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
    }
    
    public boolean buscarCodigoEan(String codEan) throws ServiceException{
    	try {
    		Optional<ProductoDto> optProductoCodAen= this.findById(Long.parseLong(codEan.substring(7, 12)));
	        if (optProductoCodAen==null) {
	        	throw new ServiceException("El Proveedor no existe");
	        }
	        Optional<ProveedorDto> optProveedorCodAen= proveedorService.findById(Long.parseLong(codEan.substring(0, 7)));
	        if (optProveedorCodAen==null) {
	        	throw new ServiceException("El Producto no existe");
	        }
	        int destinoIdCodAen = Integer.parseInt(codEan.substring(12,13));
	        
	        Optional<DestinoDto> optDestinoCodAen= destinoService.findById(BDUtil.getDestinoId(destinoIdCodAen));
	        if (optDestinoCodAen ==null) {
	        	throw new ServiceException("El Destino no existe");
	        }
    	} catch (Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
    	return true;
    }

    
}
