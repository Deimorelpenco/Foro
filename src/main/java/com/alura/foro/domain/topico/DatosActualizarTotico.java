package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.usuario.Usuario;

public record DatosActualizarTotico(

        String titulo,

        String mensaje,

        StatusTopico status,

        Usuario autor,

        Curso curso) {
}
