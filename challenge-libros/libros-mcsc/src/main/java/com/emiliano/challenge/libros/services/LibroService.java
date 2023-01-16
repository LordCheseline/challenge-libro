package com.emiliano.challenge.libros.services;

import com.emiliano.challenge.libros.models.entity.Libro;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface LibroService {
    List<Libro> listAll();
    Optional<Libro> byId(Long id);

    Libro save(Libro libro);

    void delete(Long id);
}
