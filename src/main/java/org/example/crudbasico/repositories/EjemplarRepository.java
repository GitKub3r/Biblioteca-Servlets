package org.example.crudbasico.repositories;

import org.example.crudbasico.entities.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
}
