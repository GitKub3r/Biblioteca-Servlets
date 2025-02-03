package org.example.crudbasico.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", nullable = false, length = 15)
    @NotBlank
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "El nombre solo debe contener caracteres alfanuméricos y espacios")
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@gmail\\.com", message = "El email debe ser una dirección válida de Gmail")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 4, max = 12, message = "La contraseña debe tener entre 4 y 12 caracteres")
    private String password;

    @Lob
    @Column(name = "tipo", nullable = false)
    @NotBlank
    @Pattern(regexp = "Normal|Administrador", message = "El tipo debe ser Normal o Administrador")
    private String tipo;

    @Column(name = "penalizacionHasta")
    private LocalDate penalizacionHasta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getPenalizacionHasta() {
        return penalizacionHasta;
    }

    public void setPenalizacionHasta(LocalDate penalizacionHasta) {
        this.penalizacionHasta = penalizacionHasta;
    }

}