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
import com.petama.es.appMercadonaExamen.repository.adminitracion.DestinoRepository;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@ActiveProfiles("test")
public class DestinoRepositoryTests {

    @Autowired
    private DestinoRepository destinoRepository;

    private DestinoEntity destinoEntity;

    @BeforeEach
    void setup(){
    	destinoEntity = DestinoEntity.builder()
                .nombre("Prueba Mercadona")
                .estado("1")
                .build();
    }

    @DisplayName("Test save")
    @Test
    void testSave(){
        //given - Condición previa o configuración
    	DestinoEntity destionoEntity = DestinoEntity.builder()
                .nombre("Prueba Mercadona 3")
                .estado("1")
                .build();

        //when - Acción o el comportamiento que vamos a probar
        DestinoEntity destinoGuardado = destinoRepository.save(destionoEntity);

        //then - verificar la salida
        assertThat(destinoGuardado).isNotNull();
        assertThat(destinoGuardado.getId()).isGreaterThan(0);
    }
    
    @DisplayName("Test para findAll")
    @Test
    void testFindAll(){
        //given
        DestinoEntity odestinoEntity = DestinoEntity.builder()
                .nombre("Prueba Mercadona 2")
                .estado("1")
                .build();
        destinoRepository.save(destinoEntity);
        destinoRepository.save(odestinoEntity);
        
        //when
        List<DestinoEntity> destinos = destinoRepository.findAll();

        //then
        assertThat(destinos).isNotNull();
        assertThat(destinos.size()).isEqualTo(2);
    }

    @DisplayName("Test findById")
    @Test
    void testFindById(){
        destinoRepository.save(destinoEntity);

        //when - comportamiento o accion que vamos a probar
        DestinoEntity odestinoEntity = destinoRepository.findById(destinoEntity.getId()).get();

        //then
        assertThat(odestinoEntity).isNotNull();
    }

    @DisplayName("Test update")
    @Test
    void testUpdate(){
        destinoRepository.save(destinoEntity);

        //when
        DestinoEntity odestinoEntity = destinoRepository.findById(destinoEntity.getId()).get();
        odestinoEntity.setNombre("Prueba Mercadona NEW");
        DestinoEntity odestinoEntityModificado = destinoRepository.save(odestinoEntity);

        //then
        assertThat(odestinoEntityModificado.getEstado()).isEqualTo("1");
        assertThat(odestinoEntityModificado.getNombre()).isEqualTo("Prueba Mercadona NEW");
    }

    @DisplayName("Test delete")
    @Test
    void testDelete(){
        destinoRepository.save(destinoEntity);
        //when
        destinoRepository.deleteById(destinoEntity.getId());
        Optional<DestinoEntity> empleadoOptional = destinoRepository.findById(destinoEntity.getId());
        //then
        assertThat(empleadoOptional).isEmpty();
    }
}
