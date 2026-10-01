package com.project.apifilme.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(nullable = false)
    private Integer anoLancamento;

    protected Filme() {
    }

    public Filme(String titulo, String genero, Integer anoLancamento) {
        atualizar(titulo, genero, anoLancamento);
    }

    public void atualizar(String titulo, String genero, Integer anoLancamento) {
        this.titulo = titulo.trim();
        this.genero = genero.trim();
        this.anoLancamento = anoLancamento;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public Integer getAnoLancamento() { return anoLancamento; }
}
