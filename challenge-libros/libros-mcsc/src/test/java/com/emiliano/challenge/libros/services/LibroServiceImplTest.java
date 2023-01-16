package com.emiliano.challenge.libros.services;

import com.emiliano.challenge.libros.models.entity.Libro;
import com.emiliano.challenge.libros.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * tests de implementacion de servicio de libros
 * @author Emiliano Arri
 */
@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    private final String TITULO1 = "Dracula";
    private final String AUTOR1 = "Bram Stoker";
    private final float PRECIO1 = 22.35F;
    private final Date LANZAMIENTO1 = Date.valueOf("1897-05-26");
    private final String TITULO2 = "Fundacion";
    private final String AUTOR2 = "Isaac Asimov";
    private final float PRECIO2 = 137.19F;
    private final Date LANZAMIENTO2 = Date.valueOf("1948-05-01");
    private final String TITULO3 = "Neuromante";
    private final String AUTOR3 = "William Gibson";
    private final float PRECIO3 = 108.50F;
    private final Date LANZAMIENTO3 = Date.valueOf("1984-07-01");
    @Mock
    private LibroRepository libroRepo;
    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //this.libroRepo = mock(LibroRepository.class);
        //this.libroService = mock(LibroServiceImpl.class);
    }

    @Test
    void testListAll() {

        List<Libro> list = new ArrayList<Libro>();
        Libro libro1 = crearLibro(TITULO1, AUTOR1, PRECIO1, LANZAMIENTO1);
        Libro libro2 = crearLibro(TITULO2, AUTOR2, PRECIO2, LANZAMIENTO2);
        Libro libro3 = crearLibro(TITULO3, AUTOR3, PRECIO3, LANZAMIENTO3);
        list.add(libro1);
        list.add(libro2);
        list.add(libro3);

        when(libroService.listAll()).thenReturn(list);
        List<Libro> listaLibro = libroService.listAll();

        assertEquals(3, listaLibro.size());
        verify(libroRepo, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long libroId = 1L;
        Libro libro1 = crearLibro(TITULO1, AUTOR1, PRECIO1, LANZAMIENTO1);

        when(libroService.byId(libroId)).thenReturn(Optional.of(libro1));
        Optional<Libro> libroExp = libroService.byId(libroId);

        assertTrue(libroExp.isPresent());
        assertEquals(libroExp.get().getTitulo(), libro1.getTitulo());
        verify(libroRepo, times(1)).findById(libroId);

    }

    @Test
    void testSave() {
        Libro libro1 = crearLibro(TITULO1, AUTOR1, PRECIO1, LANZAMIENTO1);

        when(libroService.save(libro1)).thenReturn(libro1);
        Libro libroExp = libroService.save(libro1);

        assertEquals(libroExp.getTitulo(), libro1.getTitulo());
        verify(libroRepo, times(1)).save(libro1);
    }

    @Test
    void testUpdate() {
        Libro libro1 = crearLibro(TITULO1, AUTOR1, PRECIO1, LANZAMIENTO1);

        when(libroService.save(libro1)).thenReturn(libro1);
        Libro libroExp = libroService.save(libro1);

        assertEquals(libroExp.getTitulo(), libro1.getTitulo());

        libro1.setTitulo(TITULO2);
        libroExp = libroService.save(libro1);

        assertEquals(libroExp.getTitulo(), TITULO2);
        verify(libroRepo, times(2)).save(libro1);
    }

    @Test
    void testDelete() {
        long libroId = 8L;

        doNothing().when(libroRepo).deleteById(isA(Long.class));
        libroService.delete(libroId);

        verify(libroRepo, times(1)).deleteById(libroId);

    }

    private Libro crearLibro(String titulo, String autor, float precio, Date lanzamiento) {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setPrecio(precio);
        libro.setLanzamiento(lanzamiento);
        return libro;
    }
}