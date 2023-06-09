package com.alura.foro.domain.topico;

import java.time.LocalDateTime;

public record DetalleTopico(String titulo, String mensaje, LocalDateTime fecha_creacion, String status, Long autor, Long curso) {
}
