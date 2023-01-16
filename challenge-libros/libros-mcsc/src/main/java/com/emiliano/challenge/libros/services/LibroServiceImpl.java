package com.emiliano.challenge.libros.services;

import com.emiliano.challenge.libros.models.entity.Libro;
import com.emiliano.challenge.libros.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion de {@link LibroService}
 * @author Emiliano Arri
 */
@Service
public class LibroServiceImpl implements LibroService{
    @Autowired
    private LibroRepository repository;
    @Override
    @Transactional(readOnly = true)
    public List<Libro> listAll() {
      return (List<Libro>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> byId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Libro save(Libro libro) {
        return repository.save(libro);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
