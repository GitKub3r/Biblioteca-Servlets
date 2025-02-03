package org.example.crudbasico.controllers;

import jakarta.validation.Valid;
import org.example.crudbasico.entities.Prestamo;
import org.example.crudbasico.entities.Usuario;
import org.example.crudbasico.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CacheConfig(cacheNames = {"users"})
public class ControllerUsuario{
    UserRepository repo;

    public ControllerUsuario() {}

    @Autowired
    public ControllerUsuario(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = repo.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Usuario> getUsuarioByID(@PathVariable int id) {
        try {
            Thread.sleep(2000);

            Usuario user = repo.findById(id).orElseThrow();
            return ResponseEntity.ok(user);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<String> addUsuarios(@Valid @RequestBody Usuario usuario) {
        repo.save(usuario);
        return ResponseEntity.ok("Se ha añadido el usuario");
    }

    @PutMapping
    public ResponseEntity<String> updateUsuarios(@Valid @RequestBody Usuario usuario) {
        repo.save(usuario);
        return ResponseEntity.ok("Se ha actualizado el usuario");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuarios(@PathVariable int id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Se ha eliminado el usuario");
    }
}
