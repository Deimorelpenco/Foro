package com.alura.foro.controller;

import com.alura.foro.domain.usuario.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Operation(summary = "Cargar", description = "Cargar un usuarios.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosUsuario datosUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = passwordEncoder.encode(datosUsuario.contrasena());
        Usuario usuario = usuarioRepository.save(new Usuario(datosUsuario.nombre(), datosUsuario.email(),
                contrasenaEncriptada));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
              datosUsuario.contrasena(), usuario.getActivo().toString());
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un usuarios.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping
    public ResponseEntity<Page<DatosListaUsuario>>  listaUsuario(Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findByActivoTrue(paginacion).map(DatosListaUsuario::new));
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar GET" ,required=true)
    @Operation(summary = "Ver", description = "Ver un usuarios.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @GetMapping("/{id}")
    public ResponseEntity<DetalleUsuario> detalleUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario = new DetalleUsuario(usuario.getNombre(), usuario.getEmail());
        return ResponseEntity.ok(datosUsuario);
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar PUT" ,required=true)
    @Operation(summary = "Modificar", description = "Modificar un usuarios.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                usuario.getContrasena(), usuario.getActivo().toString()));
    }

    @SecurityRequirement(name = "bearer-key")
    @Parameter(in= ParameterIn.HEADER,name = "Authorization", description = "Necesita el JWT Token para poder usar DELETE" ,required=true)
    @Operation(summary = "Eliminar", description = "Eliminar un usuarios.")
    @ApiResponse(responseCode = "200", description = "OK",content=@Content)
    @ApiResponse(responseCode = "403", description = "Forbidden",content=@Content)
    @ApiResponse(responseCode = "404", description = "Not Found",content=@Content)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
        return ResponseEntity.noContent().build();
    }

}