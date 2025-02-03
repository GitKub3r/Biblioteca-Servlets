package org.example.crudbasico.repositories;

import org.example.crudbasico.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
    public Usuario findByEmail(String email);
}
