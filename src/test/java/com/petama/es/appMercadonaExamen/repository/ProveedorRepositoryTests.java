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
import com.petama.es.appMercadonaExamen.entity.administracion.ProveedorEntity;
import com.petama.es.appMercadonaExamen.repository.adminitracion.ProveedorRepository;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@ActiveProfiles("test")
public class ProveedorRepositoryTests {

    @Autowired
    private ProveedorRepository proveedorRepository;

    private ProveedorEntity proveedorEntity;

    @BeforeEach
    void setup(){
    	proveedorEntity = ProveedorEntity.builder()
                .ruc("12345678954")
    			.razonSocial("Prueba Proveedor")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
    }

    @DisplayName("Test save")
    @Test
    void testSave(){
        //given - Condición previa o configuración
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
                .ruc("12345678988")
    			.razonSocial("Prueba Proveedor ff")
    			.movil("63245854")
    			.email("hola@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();

        //when - Acción o el comportamiento que vamos a probar
        ProveedorEntity proveedorGuardado = proveedorRepository.save(oProveedorEntity);

        //then - verificar la salida
        assertThat(proveedorGuardado).isNotNull();
        assertThat(proveedorGuardado.getIdProveedor()).isGreaterThan(0);
    }
    
    @DisplayName("Test para findAll")
    @Test
    void testFindAll(){
        //given
    	ProveedorEntity oProveedorEntity = ProveedorEntity.builder()
        		.ruc("12345678956")
    			.razonSocial("Prueba Proveedor 2")
    			.movil("63245854")
    			.email("hola2@gmail.com")
    			.representanteLegal("Maria Rosas")
                .estado("1")
                .build();
        
        proveedorRepository.save(proveedorEntity);
        proveedorRepository.save(oProveedorEntity);
        
        //when
        List<ProveedorEntity> proveedores = proveedorRepository.findAll();

        //then
        assertThat(proveedores).isNotNull();
        assertThat(proveedores.size()).isEqualTo(2);
    }

    @DisplayName("Test findById")
    @Test
    void testFindById(){
        proveedorRepository.save(proveedorEntity);

        //when - comportamiento o accion que vamos a probar
        ProveedorEntity oProveedorEntity = proveedorRepository.findById(proveedorEntity.getIdProveedor()).get();

        //then
        assertThat(oProveedorEntity).isNotNull();
    }

    
    @DisplayName("Test update")
    @Test
    void testUpdate(){
    	proveedorRepository.save(proveedorEntity);

        //when
    	ProveedorEntity oProveedorEntity = proveedorRepository.findById(proveedorEntity.getIdProveedor()).get();
    	oProveedorEntity.setRazonSocial("Prueba Proveedor update");
        ProveedorEntity oProveedorEntityModificado = proveedorRepository.save(oProveedorEntity);

        //then
        assertThat(oProveedorEntityModificado.getEstado()).isEqualTo("1");
        assertThat(oProveedorEntityModificado.getRazonSocial()).isEqualTo("Prueba Proveedor update");
    }

    @DisplayName("Test delete")
    @Test
    void testDelete(){
        proveedorRepository.save(proveedorEntity);
        //when
        proveedorRepository.deleteById(proveedorEntity.getIdProveedor());
        Optional<ProveedorEntity> proveedorOptional = proveedorRepository.findById(proveedorEntity.getIdProveedor());
        //then
        assertThat(proveedorOptional).isEmpty();
    }
}
