package com.petama.es.appMercadonaExamen.dto.administracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProveedorDto {

	private Long idProveedor;

	@NotBlank(message = "El ruc no puede estar vacio")
    @NotNull(message = "El ruc no puede ser nulo")
	@Size(min=11,max=11, message="El RUC debe contener un mínimo {min} y un máximo {max} de carácteres")
	private String ruc;
	
	@NotBlank(message = "La razon Social no puede estar vacio")
    @NotNull(message = "La razon Social no puede ser nulo")
	private String razonSocial;

	@Pattern(regexp="^[0-9]{1,11}",message="El movil solo soporta números")
	@Size(min=7,max=11, message="El movil debe contener un mínimo {min} y un máximo {max} de carácteres")
	private String movil;
	
	@Email(message="Ingresar un email con el formato correcto")
	@Size(max=100, message="El email debe contener un máximo {max} de carácteres")
	private String email;
	
	@Pattern(regexp="^[A-Za-z ]{1,150}",message="El representante legal Solo soporta letras") 
	@Size(min=10,max=150, message="El representante legal debe contener un mínimo {min} y un máximo {max} de carácteres")
	private String representanteLegal;
	
	@Pattern(regexp="^[0-1]{1}",message="Solo soporta el estado 1=activo 0=inactivo") 
    private String estado;}
