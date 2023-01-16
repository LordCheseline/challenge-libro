package com.emiliano.challenge.libros.repositories;

import com.emiliano.challenge.libros.models.entity.Libro;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio de Libros
 *
 * @author Emiliano Arri
 */
public interface LibroRepository extends CrudRepository<Libro,Long> {
}
