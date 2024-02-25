package com.petama.es.appMercadonaExamen.controller.administracion;


import static com.petama.es.appMercadonaExamen.common.GlobalConstant.API_PROVEEDOR;
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
import org.springframework.web.bind.annotation.RestController;

import com.petama.es.appMercadonaExamen.dto.administracion.ProveedorDto;
import com.petama.es.appMercadonaExamen.service.administracion.ProveedorService;
import com.petama.es.appMercadonaExamen.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(API_PROVEEDOR)
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(final ProveedorService proveedorService){
        this.proveedorService=proveedorService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try {

            List<ProveedorDto> proveedors= proveedorService.findAll();
            if (proveedors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(proveedors);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/Active")
    public ResponseEntity<?> findAllActive(){
        try {

            List<ProveedorDto> proveedors= proveedorService.findAllActive();
            if (proveedors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(proveedors);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id ){
        try {

            Optional<ProveedorDto> optProveedor= proveedorService.findById(id);
            if (optProveedor==null) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(optProveedor.get());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
	public ResponseEntity<?> save(@RequestBody @Validated ProveedorDto proveedorDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			ProveedorDto oProveedorDto=proveedorService.save(proveedorDto);
			if (isNull(oProveedorDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oProveedorDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Validated ProveedorDto proveedorDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			proveedorDto.setIdProveedor(id);
			ProveedorDto oProveedorDto=proveedorService.update(proveedorDto);
			if (isNull(oProveedorDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oProveedorDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			ProveedorDto proveedorDto= new ProveedorDto();
			proveedorDto.setIdProveedor(id);
			if (proveedorService.delete(proveedorDto)) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
