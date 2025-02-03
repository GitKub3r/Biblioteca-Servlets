package org.example.crudbasico.controllers;

import org.example.crudbasico.entities.Libro;
import org.example.crudbasico.entities.Usuario;
import org.example.crudbasico.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@CacheConfig(cacheNames = {"books"})
public class ControllerLibro {
    LibroRepository repo;

    public ControllerLibro() {}

    @Autowired
    public ControllerLibro(LibroRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAllBooks() {
        List<Libro> books = repo.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Libro> getBookById(@PathVariable String id) {
        try {
            Thread.sleep(2000);

            Libro book = repo.findById(id).orElseThrow();
            return ResponseEntity.ok(book);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping()
    public ResponseEntity<String> addBook(@Valid @RequestBody Libro book) {
        repo.save(book);
        return ResponseEntity.ok("Se ha añadido el libro");
    }

    @PutMapping()
    public ResponseEntity<String> updateBook(@Valid @RequestBody Libro book) {
        repo.save(book);
        return ResponseEntity.ok("Se ha actualizado el libro");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        Libro book = repo.findById(id).orElseThrow();
        repo.delete(book);
        return ResponseEntity.ok("Se ha eliminado el libro");
    }

}
