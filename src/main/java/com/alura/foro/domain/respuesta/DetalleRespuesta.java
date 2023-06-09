package com.alura.foro.domain.respuesta;

import java.time.LocalDateTime;

public record DetalleRespuesta(String mensaje, Long topico, LocalDateTime fecha_creacion, Long autor, Boolean solucion) {
}
