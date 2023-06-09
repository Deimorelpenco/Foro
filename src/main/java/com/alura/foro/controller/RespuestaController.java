package com.alura.foro.controller;

import com.alura.foro.domain.respuesta.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar POST" ,required=true)
    @Operation(summary = "Cargar", description = "Cargar un respuestas.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PostMapping
    public ResponseEntity<DatosRespuestaRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                             UriComponentsBuilder uriComponentsBuilder){
        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta));
        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(respuesta.getId(),
                respuesta.getMensaje(), respuesta.getTopico().getId(), respuesta.getFecha_creacion(),
                respuesta.getAutor().getId(), respuesta.getSolucion().toString());
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un respuestas.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listaRespuesta(Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosListaRespuesta::new));
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un respuestas.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping("/{id}")
    public ResponseEntity<DetalleRespuesta> detalleRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var datosRespuesta = new DetalleRespuesta(respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getFecha_creacion(), respuesta.getAutor().getId(), respuesta.getSolucion());
        return ResponseEntity.ok(datosRespuesta);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar PUT" ,required=true)
    @Operation(summary = "Modificar", description = "Modificar un respuestas.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getFecha_creacion(), respuesta.getAutor().getId(), respuesta.getSolucion().toString()));
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar DELETE" ,required=true)
    @Operation(summary = "Eliminar", description = "Eliminar un respuestas.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuestaRepository.delete(respuesta);
        return ResponseEntity.noContent().build();
    }

}
