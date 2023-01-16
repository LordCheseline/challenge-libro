package com.emiliano.challenge.libros.controller;

import com.emiliano.challenge.libros.models.entity.Libro;
import com.emiliano.challenge.libros.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *Controlador principal
 *
 * @author Emiliano Arri
 */
@RestController
public class LibroController {
    @Autowired
    private LibroService service;

    /**
     * Lista todos los libros
     *
     * @return ResponseEntity
     */
    @GetMapping("/list")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    /**
     * Busca un Libro
     * @param id
     * @return ResponseEntity
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.byId(id);
        if (libroOptional.isPresent()) {
            return ResponseEntity.ok(libroOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Guarda un libro en la BBDD
     * @param libro
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<?> createLibro(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return getErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(libro));
    }

    /**
     * Actualiza la informacion de un libro.
     * @param id
     * @param libro
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editLibro(@PathVariable Long id, @Valid @RequestBody Libro libro, BindingResult result) {
        Optional<Libro> libroOptional = service.byId(id);
        if (result.hasErrors()) {
            return getErrors(result);
        }
        if (libroOptional.isPresent()) {
            Libro libroDb = libroOptional.get();
            libroDb.setTitulo(libro.getTitulo());
            libroDb.setAutor(libro.getAutor());
            libroDb.setPrecio(libro.getPrecio());
            libroDb.setLanzamiento(libro.getLanzamiento());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(libroDb));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * borra un libro
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.byId(id);
        if (libroOptional.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Lista los errores y los devuelve al usuario.
     * @return ResponseEntity
     */
    private static ResponseEntity<Map<String, String>> getErrors(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
