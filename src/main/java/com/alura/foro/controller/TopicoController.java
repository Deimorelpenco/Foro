package com.alura.foro.controller;

import com.alura.foro.domain.topico.*;
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
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepositiry topicoRepositiry;

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar POST" ,required=true)
    @Operation(summary = "Cargar", description = "Cargar un topicos.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepositiry.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFecha_creacion(), topico.getStatus().toString(), topico.getAutor().getId(), topico.getCurso().getId());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un topicos.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listaTopico(Pageable paginacion){
        return ResponseEntity.ok(topicoRepositiry.findAll(paginacion).map(DatosListaTopico::new));
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un topicos.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping("/{id}")
    public ResponseEntity<DetalleTopico> detalleTopico(@PathVariable Long id){
        Topico topico = topicoRepositiry.getReferenceById(id);
        var datosTopico = new DetalleTopico(topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(),
                topico.getStatus().toString(), topico.getAutor().getId(), topico.getCurso().getId());
        return ResponseEntity.ok(datosTopico);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar PUT" ,required=true)
    @Operation(summary = "Modificar", description = "Modificar un topicos.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTotico datosActualizarTotico) {
        Topico topico = topicoRepositiry.getReferenceById(id);
        topico.actualizarDatos(datosActualizarTotico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFecha_creacion(), topico.getStatus().toString(), topico.getAutor().getId(), topico.getCurso().getId()));

    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar DELETE" ,required=true)
    @Operation(summary = "Eliminar", description = "Elimina un topicos.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepositiry.getReferenceById(id);
        topicoRepositiry.delete(topico);
        return ResponseEntity.noContent().build();
    }

}
