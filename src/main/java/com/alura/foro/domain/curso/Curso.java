package com.alura.foro.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Cursos")
@Entity(name = "Curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;

    public Curso(DatosCurso curso) {
        this.nombre = curso.nombre();
        this.categoria = curso.categoria();
    }

    public Curso(Curso curso) {
        this.id = curso.getId();
    }
}
