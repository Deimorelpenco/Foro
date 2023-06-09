package com.alura.foro.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(Long id, String mensaje, Long topico, LocalDateTime fecha_creacion,
                                      Long autor, String solucion) {
}
