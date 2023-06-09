package com.alura.foro.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDateTime fefha_creacion, String status,
                                   Long autor, Long curso) {
}
