package org.example.crudbasico.controllers;

import org.example.crudbasico.entities.Ejemplar;
import org.example.crudbasico.entities.Libro;
import org.example.crudbasico.repositories.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/copies")
@CacheConfig(cacheNames = {"copies"})
public class ControllerEjemplar {
    EjemplarRepository repo;

    public ControllerEjemplar() {}

    @Autowired
    public ControllerEjemplar(EjemplarRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Ejemplar>> getAllEjemplares() {
        List<Ejemplar> ejemplares = repo.findAll();
        return ResponseEntity.ok(ejemplares);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Ejemplar> getEjemplaresById(@PathVariable int id) {
        try {
            Thread.sleep(2000);

            Ejemplar copy = repo.findById(id).orElseThrow();
            return ResponseEntity.ok(copy);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<String> addEjemplare(@Valid @RequestBody Ejemplar ejemplar) {
        repo.save(ejemplar);
        return ResponseEntity.ok("Se ha añadido el ejemplar");
    }

    @PutMapping
    public ResponseEntity<String> updateEjemplar(@Valid @RequestBody Ejemplar ejemplar) {
        repo.save(ejemplar);
        return ResponseEntity.ok("Se ha actualizado el ejemplar");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable int id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Se ha eliminado el ejemplar");
    }

}
