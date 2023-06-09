package com.alura.foro.domain.topico;


import java.time.LocalDateTime;

public record DatosListaTopico(String titulo, String mensaje, LocalDateTime fecha_creacion, String status, Long autor, Long curso) {

    public DatosListaTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(), topico.getStatus().toString(), topico.getAutor().getId(), topico.getCurso().getId());
    }

}
