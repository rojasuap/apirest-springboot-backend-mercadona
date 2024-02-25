package com.petama.es.appMercadonaExamen.entity.administracion;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_DESTINO")
@Entity(name = "DestinoEntity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "DESTINO_ID")
	// Sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDestino")
	@SequenceGenerator(sequenceName = "SEQ_DESTINO", allocationSize = 1, name = "seqDestino")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(name = "NOMBRE")
    private String nombre;  // Mínimo 10 y máximo 240

    @Column(name = "ESTADO")
    private String estado;
}
