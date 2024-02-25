package com.petama.es.appMercadonaExamen.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.petama.es.appMercadonaExamen.entity.administracion.DestinoEntity;
import com.petama.es.appMercadonaExamen.entity.administracion.ProductoEntity;
import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;
import com.petama.es.appMercadonaExamen.repository.adminitracion.DestinoRepository;
import com.petama.es.appMercadonaExamen.repository.adminitracion.ProductoRepository;
import com.petama.es.appMercadonaExamen.repository.adminitracion.ProveedorRepository;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTests {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private DestinoRepository destinoRepository;

    private ProveedorEntity proveedorEntity;
    private ProductoEntity productoEntity;
    private DestinoEntity destinoEntity;
    
    @BeforeEach
    void setup(){
    	proveedorEntity = ProveedorEntity.builder()
                .idProveedor(1L)
    			.ruc("12345678954")
    			.razonSocial("Prueba Proveedor")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    	destinoEntity = DestinoEntity.builder()
    			.id(1L)
    			.nombre("Prueba Destino 1")
    			.estado("1")
                .build();
    	productoEntity = ProductoEntity.builder()
    			.id(1L)
    			.nombre("Prueba Producto")
    			.precio(100.00)
    			.stock(15.00)
    			.tienda(1)
    			.proveedor(proveedorEntity)
    			.destino(destinoEntity)
                .estado("1")
                .build();
    }

    @DisplayName("Test save")
    @Test
    void testSave(){
        //given
    	DestinoEntity oDestinoEntity = DestinoEntity.builder()
    			.id(2L)
    			.nombre("Prueba Destino 2")
    			.estado("1")
                .build();
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
                .idProveedor(2L)
    			.ruc("12345678954")
    			.razonSocial("Prueba Proveedor 2")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    	
    	proveedorRepository.save(oProveedorEntity);
    	destinoRepository.save(oDestinoEntity);
        
    	ProductoEntity oProductoEntity = ProductoEntity.builder()
                .nombre("Prueba Producto 2")
    			.precio(101.00)
    			.stock(16.00)
    			.tienda(1)
    			.proveedor(oProveedorEntity)
    			.destino(oDestinoEntity)
                .estado("1")
                .build();

        //when
    	ProductoEntity productoGuardado = productoRepository.save(oProductoEntity);

        //then
        assertThat(productoGuardado).isNotNull();
        assertThat(productoGuardado.getId()).isGreaterThan(0);
    }
    
    @DisplayName("Test para findAll")
    @Test
    void testFindAll(){
        //given
    	ProductoEntity oProductoEntity = ProductoEntity.builder()
                .nombre("Prueba Producto ee")
    			.precio(122.00)
    			.stock(10.00)
    			.tienda(1)
    			.proveedor(proveedorEntity)
    			.destino(destinoEntity)
                .estado("1")
                .build();

    	proveedorRepository.save(proveedorEntity);
    	destinoRepository.save(destinoEntity);
        productoRepository.save(productoEntity);
        productoRepository.save(oProductoEntity);
        
        //when
        List<ProductoEntity> productos = productoRepository.findAll();

        //then
        assertThat(productos).isNotNull();
        assertThat(productos.size()).isEqualTo(2);
    }

    @DisplayName("Test findById")
    @Test
    void testFindById(){
    	//given
    	DestinoEntity oDestinoEntity = DestinoEntity.builder()
    			.id(3L)
    			.nombre("Prueba Destino 2")
    			.estado("1")
                .build();
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
                .idProveedor(3L)
    			.ruc("12345678954")
    			.razonSocial("Prueba Proveedor 2")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    	proveedorRepository.save(oProveedorEntity);
    	destinoRepository.save(oDestinoEntity);
    	
    	ProductoEntity oProductoEntity = ProductoEntity.builder()
                .nombre("Prueba Producto 4")
    			.precio(122.00)
    			.stock(10.00)
    			.tienda(1)
    			.proveedor(oProveedorEntity)
    			.destino(oDestinoEntity)
                .estado("1")
                .build();
    	oProductoEntity = productoRepository.save(oProductoEntity);
    	
        //when
        ProductoEntity oProductoEntityUbicado = productoRepository.findById(oProductoEntity.getId()).get();

        //then
        assertThat(oProductoEntityUbicado).isNotNull();
    }

    
    @DisplayName("Test update")
    @Test
    void testUpdate(){
    	//given
    	DestinoEntity oDestinoEntity = DestinoEntity.builder()
    			.id(4L)
    			.nombre("Prueba Destino 2")
    			.estado("1")
                .build();
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
                .idProveedor(4L)
    			.ruc("12345678954")
    			.razonSocial("Prueba Proveedor 2")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    	proveedorRepository.save(oProveedorEntity);
    	destinoRepository.save(oDestinoEntity);
    	
    	ProductoEntity oProductoEntity = ProductoEntity.builder()
                .nombre("Prueba Producto 5")
    			.precio(122.00)
    			.stock(10.00)
    			.tienda(1)
    			.proveedor(oProveedorEntity)
    			.destino(oDestinoEntity)
                .estado("1")
                .build();
    	ProductoEntity oProductoEntityNew = productoRepository.save(oProductoEntity);
    	
        //when
    	ProductoEntity oProductoEntity2 = productoRepository.findById(oProductoEntityNew.getId()).get();
    	oProductoEntity2.setNombre("Prueba Producto update");
        ProductoEntity oProductoEntityModificado = productoRepository.save(oProductoEntity2);

        //then
        assertThat(oProductoEntityModificado.getEstado()).isEqualTo("1");
        assertThat(oProductoEntityModificado.getNombre()).isEqualTo("Prueba Producto update");
    }

    @DisplayName("Test delete")
    @Test
    void testDelete(){
    	//given
    	DestinoEntity oDestinoEntity = DestinoEntity.builder()
    			.id(7L)
    			.nombre("Prueba Destino ii")
    			.estado("1")
                .build();
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
                .idProveedor(7L)
    			.ruc("12345678955")
    			.razonSocial("Prueba Proveedor ii")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    	
    	ProductoEntity oProductoEntity = ProductoEntity.builder()
                .nombre("Prueba Producto 22")
    			.precio(122.00)
    			.stock(10.00)
    			.tienda(1)
    			.proveedor(oProveedorEntity)
    			.destino(oDestinoEntity)
                .estado("1")
                .build();
    	ProductoEntity oProductoEntityNew = productoRepository.save(oProductoEntity);
    	
    	//when
        productoRepository.deleteById(oProductoEntity.getId());
        Optional<ProductoEntity> productoOptional = productoRepository.findById(oProductoEntity.getId());
        //then
        assertThat(productoOptional).isEmpty();
    }
}
