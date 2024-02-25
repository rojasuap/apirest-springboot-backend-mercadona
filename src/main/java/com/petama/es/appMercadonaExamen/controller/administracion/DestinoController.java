package com.petama.es.appMercadonaExamen.controller.administracion;


import static com.petama.es.appMercadonaExamen.common.GlobalConstant.API_DESTINO;
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

import com.petama.es.appMercadonaExamen.dto.administracion.DestinoDto;
import com.petama.es.appMercadonaExamen.service.administracion.DestinoService;
import com.petama.es.appMercadonaExamen.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(API_DESTINO)
public class DestinoController {

    private final DestinoService destinoService;

    public DestinoController(final DestinoService destinoService){
        this.destinoService=destinoService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        try {

            List<DestinoDto> destinos= destinoService.findAll();
            if (destinos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(destinos);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/Active")
    public ResponseEntity<?> findAllActive(){
        try {

            List<DestinoDto> destinos= destinoService.findAllActive();
            if (destinos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(destinos);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id ){
        try {

            Optional<DestinoDto> optDestino= destinoService.findById(id);
            if (optDestino != null) {
                return ResponseEntity.noContent().build();
            }
            return  ResponseEntity.ok(optDestino.get());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
	public ResponseEntity<?> save(@RequestBody @Validated DestinoDto destinoDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			DestinoDto oDestinoDto=destinoService.save(destinoDto);
			if (isNull(oDestinoDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oDestinoDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Validated DestinoDto destinoDto, BindingResult result){
		if (result.hasErrors()) {
			return WebUtil.getErrors(result);
		}
		try {
			destinoDto.setId(id);
			DestinoDto oDestinoDto=destinoService.update(destinoDto);
			if (isNull(oDestinoDto)) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(oDestinoDto);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			DestinoDto destinoDto= new DestinoDto();
			destinoDto.setId(id);
			if (destinoService.delete(destinoDto)) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
