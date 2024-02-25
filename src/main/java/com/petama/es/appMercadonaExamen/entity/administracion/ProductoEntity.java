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
@Table(name = "TBL_PRODUCTO")
@Entity(name = "ProductoEntity")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoEntity {

    @Id
    @Column(name = "PRODUCTO_ID")
    // Sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProducto")
    @SequenceGenerator(sequenceName = "SEQ_PRODUCTO", allocationSize = 1, name = "seqProducto")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;  // Mínimo 10 y máximo 240

    @Column(name = "PRECIO")// Mayor que cero
    private Double precio;

    @Column(name = "STOCK") // Mayor o igual que cero
    private Double stock;

    @Column(name = "COD_EAN")
    private String codEan;
    
    @Column(name = "TIENDA")
    private long tienda;
    
    @OneToOne
	@JoinColumn(name = "DESTINO_ID", nullable = false)
	private DestinoEntity destino;
    
    @OneToOne
	@JoinColumn(name = "PROVEEDOR_ID", nullable = false)
	private ProveedorEntity proveedor;
    
    @Column(name = "ESTADO")
    private String estado;
}
