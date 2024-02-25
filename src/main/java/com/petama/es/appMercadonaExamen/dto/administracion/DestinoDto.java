package com.petama.es.appMercadonaExamen.dto.administracion;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import javax.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinoDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min=10, max=240,message="El nombre debe contener mínimo {min} y máximo {max} caracteres")
    private String nombre;  // Mínimo 10 y máximo 240

    @Pattern(regexp="^[0-1]{1}",message="Solo soporta el estado 1=activo 0=inactivo") 
    private String estado;
}
