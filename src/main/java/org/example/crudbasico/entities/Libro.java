package org.example.crudbasico.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @NotBlank(message = "El ISBN no puede estar vacío")
    @NotNull(message = "El ISBN no puede estar vacío")
    @NotEmpty(message = "El ISBN no puede estar vacío")
    @Pattern(regexp = "^97[89]-\\d-\\d{2,5}-\\d{2,7}-\\d$\n", message = "El ISBN debe tener un formato válido")
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    @NotBlank
    @Size(max = 200)
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "El título solo debe contener caracteres alfanuméricos y espacios")
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "El autor solo debe contener caracteres alfanuméricos y espacios")
    private String autor;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

}