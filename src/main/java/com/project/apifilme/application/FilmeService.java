package com.project.apifilme.application;

import com.project.apifilme.domain.Filme;
import com.project.apifilme.infrastructure.persistence.FilmeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class FilmeService {
    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Filme> listar() {
        return repository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public Filme buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado"));
    }

    public Filme cadastrar(String titulo, String genero, Integer anoLancamento) {
        return repository.save(new Filme(titulo, genero, anoLancamento));
    }

    public Filme atualizar(Long id, String titulo, String genero, Integer anoLancamento) {
        Filme filme = buscar(id);
        filme.atualizar(titulo, genero, anoLancamento);
        return repository.save(filme);
    }

    public void excluir(Long id) {
        repository.delete(buscar(id));
    }
}
