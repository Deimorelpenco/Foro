package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.respuesta.Respuesta;
import com.alura.foro.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha_creacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor", referencedColumnName = "id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso", referencedColumnName = "id")
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuesta = new ArrayList<>();

    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.status = StatusTopico.NO_RESPONDIDO;
        this.autor = new Usuario(datosRegistroTopico.autor());
        this.curso = new Curso(datosRegistroTopico.curso());
    }

    public Topico(Topico topico) {
        this.id = topico.getId();
    }

    public void actualizarDatos(DatosActualizarTotico datosActualizarTotico) {
        if (datosActualizarTotico.titulo() != null) {
            this.titulo = datosActualizarTotico.titulo();
        }
        if (datosActualizarTotico.mensaje() != null) {
            this.mensaje = datosActualizarTotico.mensaje();
        }
        if (datosActualizarTotico.status() != null) {
            this.status = datosActualizarTotico.status();
        }
        if (datosActualizarTotico.autor() != null) {
            this.autor = datosActualizarTotico.autor();
        }
        if (datosActualizarTotico.curso() != null) {
            this.curso = datosActualizarTotico.curso();
        }

    }
}
