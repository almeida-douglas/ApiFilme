package com.project.apifilme.config;

import com.project.apifilme.domain.Filme;
import com.project.apifilme.infrastructure.persistence.FilmeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FilmesIniciais implements CommandLineRunner {
    private final FilmeRepository repository;

    public FilmesIniciais(FilmeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    new Filme("Interestelar", "Ficção científica", 2014),
                    new Filme("O Poderoso Chefão", "Drama", 1972),
                    new Filme("Toy Story", "Animação", 1995)));
        }
    }
}
