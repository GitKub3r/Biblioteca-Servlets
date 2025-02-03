package org.example.crudbasico.controllers;

import jakarta.validation.Valid;
import org.example.crudbasico.entities.Ejemplar;
import org.example.crudbasico.entities.Prestamo;
import org.example.crudbasico.repositories.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/loans")
@CacheConfig(cacheNames = {"loans"})
public class ControllerPrestamo {
    PrestamoRepository repo;

    public ControllerPrestamo() {}

    @Autowired
    public ControllerPrestamo(PrestamoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> getAllPrestamos() {
        List<Prestamo> prestamos = repo.findAll();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Prestamo> getPrestamoByID(@PathVariable int id) {
        try {
            Thread.sleep(2000);

            Prestamo loan = repo.findById(id).orElseThrow();
            return ResponseEntity.ok(loan);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<String> addPrestamos(@Valid @RequestBody Prestamo prestamo) {
        repo.save(prestamo);
        return ResponseEntity.ok("Se ha añadido el préstamo");
    }

    @PutMapping
    public ResponseEntity<String> updatePrestamo(@Valid @RequestBody Prestamo prestamo) {
        repo.save(prestamo);
        return ResponseEntity.ok("Se ha actualizado el préstamo");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrestamo(@PathVariable int id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Se ha eliminado el préstamo");
    }
}
