package com.petama.es.appMercadonaExamen.dto.administracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petama.es.appMercadonaExamen.entity.administracion.DestinoEntity;
import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoDto {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min=10, max=240,message="El nombre debe contener mínimo {min} y máximo {max} caracteres")
    private String nombre;  // Mínimo 10 y máximo 240

    @Min(value=1,message="El precio debe ser mayor que cero")
    private Double precio;// Mayor que cero

    @Positive(message = "El stock no puede ser un número negativo")
    private Double stock;// Mayor o igual que cero

    @Pattern(regexp="^[0-9]{1,13}",message="El codigo EAN solo soporta números") 
    @Size(min=13, max=13,message="El codigo EAN debe contener {max} caracteres")
    private String codEan;
    
    private long tienda;
    
	private DestinoEntity destino;
    
	private ProveedorEntity proveedor;
    
    @Pattern(regexp="^[0-1]{1}",message="Solo soporta el estado 1=activo 0=inactivo") 
    private String estado;
}
