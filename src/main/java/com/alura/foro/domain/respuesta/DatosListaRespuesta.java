package com.alura.foro.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(String mensaje, Long topico, LocalDateTime fecha_creacion, Long autor, Boolean solucion) {

    public DatosListaRespuesta(Respuesta respuesta){
        this(respuesta.getMensaje(), respuesta.getTopico().getId(), respuesta.getFecha_creacion(), respuesta.getAutor().getId(), respuesta.getSolucion());
    }
}
