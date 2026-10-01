package com.project.apifilme.infrastructure.http;

import jakarta.validation.constraints.*;

public record FilmeRequest(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres") String titulo,
        @NotBlank(message = "O gênero é obrigatório")
        @Size(max = 100, message = "O gênero deve ter no máximo 100 caracteres") String genero,
        @NotNull(message = "O ano de lançamento é obrigatório")
        @Min(value = 1888, message = "O ano de lançamento deve ser a partir de 1888")
        @Max(value = 9999, message = "O ano de lançamento deve ter no máximo quatro dígitos")
        Integer anoLancamento) {
}
