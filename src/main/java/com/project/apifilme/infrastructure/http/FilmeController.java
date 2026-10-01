package com.project.apifilme.infrastructure.http;

import com.project.apifilme.application.FilmeService;
import com.project.apifilme.domain.Filme;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Filme> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Filme buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Filme> cadastrar(@Valid @RequestBody FilmeRequest request) {
        Filme filme = service.cadastrar(request.titulo(), request.genero(), request.anoLancamento());
        return ResponseEntity.created(URI.create("/filmes/" + filme.getId())).body(filme);
    }

    @PutMapping("/{id}")
    public Filme atualizar(@PathVariable Long id, @Valid @RequestBody FilmeRequest request) {
        return service.atualizar(id, request.titulo(), request.genero(), request.anoLancamento());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
