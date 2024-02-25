package com.petama.es.appMercadonaExamen.controller.administracion;


import static com.petama.es.appMercadonaExamen.common.GlobalConstant.*;
import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petama.es.appMercadonaExamen.dto.administracion.ProductoDto;
import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.service.administracion.ProductoService;
import com.petama.es.appMercadonaExamen.service.administracion.ProveedorService;
import com.petama.es.appMercadonaExamen.util.WebUtil;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(API_PRODUCTO)
@Validated
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(final ProductoService productoService){
        this.productoService=productoService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try {

            List<ProductoDto> productos= productoService.findAll();
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/Active")
    public ResponseEntity<?> findAllActive(){
        try {

            List<ProductoDto> productos= productoService.findAllActive();
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(productos);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id ){
        try {
            Optional<ProductoDto> optProducto= productoService.findById(id);
            if (optProducto==null) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(optProducto.get());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/codigoEan")
    public ResponseEntity<?> findByCodEan(@RequestParam
    									  @Pattern(regexp="^[0-9]{1,13}",message="El codigo EAN solo soporta números") 
    									  @Size(min=13, max=13,message="El codigo EAN debe contener {max} caracteres")
    									  String codEan){
    	
    	try {
    		Boolean encontradoCod = productoService.buscarCodigoEan(codEan);
            if (encontradoCod == Boolean.FALSE) {         	
                return new ResponseEntity<>("Proveedor, Producto o Destino no existen", HttpStatus.BAD_REQUEST);
            }
    		
            Optional<ProductoDto> optProducto= productoService.findByCodigoEan(codEan);
            if (optProducto==null) {         	 
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(optProducto.get());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
	public ResponseEntity<?> save(@RequestBody @Validated ProductoDto productoDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			ProductoDto oProductoDto=productoService.save(productoDto);
			if (isNull(oProductoDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oProductoDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Validated ProductoDto productoDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			productoDto.setId(id);
			ProductoDto oProductoDto=productoService.update(productoDto);
			if (isNull(oProductoDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oProductoDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @PutMapping("/generateEan/{id}")
	public ResponseEntity<?> generateEan(@PathVariable Long id){
		try {
			ProductoDto productoDto = new ProductoDto(); 
			productoDto.setId(id);
			ProductoDto oProductoDto=productoService.generateCodigoEan(productoDto);
			if (isNull(oProductoDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oProductoDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			ProductoDto productoDto= new ProductoDto();
			productoDto.setId(id);
			if (productoService.delete(productoDto)) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}    
}
