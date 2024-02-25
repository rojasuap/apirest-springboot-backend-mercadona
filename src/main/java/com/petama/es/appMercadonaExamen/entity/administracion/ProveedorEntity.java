package com.petama.es.appMercadonaExamen.entity.administracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_PROVEEDOR")
@Entity(name = "ProveedorEntity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProveedorEntity {

	@Id
	@Column(name="PROVEEDOR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seqProveedor" )
	@SequenceGenerator(sequenceName = "SEQ_PROVEEDOR", allocationSize=1 ,name = "seqProveedor")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProveedor;
	
	@Column(name="RUC")
	private String ruc;
	
	@Column(name="Razon_Social")
	private String razonSocial;
	
	@Column(name="MOVIL")
	private String movil;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="REPRESENTANTE_LEGAL")
	private String representanteLegal;
	
	@Column(name="ESTADO")
	private String estado;}
