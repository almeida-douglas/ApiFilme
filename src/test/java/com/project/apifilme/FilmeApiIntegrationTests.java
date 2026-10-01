package com.project.apifilme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:filmes-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@Transactional
class FilmeApiIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarOsTresFilmesIniciais() throws Exception {
        mockMvc.perform(get("/filmes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].titulo").value("Interestelar"))
                .andExpect(jsonPath("$[1].titulo").value("O Poderoso Chefão"))
                .andExpect(jsonPath("$[2].titulo").value("Toy Story"));
    }

    @Test
    void deveCadastrarBuscarAtualizarEExcluir() throws Exception {
        String location = mockMvc.perform(post("/filmes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"Matrix","genero":"Ficção científica","anoLancamento":1999}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.titulo").value("Matrix"))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.anoLancamento").value(1999));

        mockMvc.perform(put(location).contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"Matrix Reloaded","genero":"Ação","anoLancamento":2003}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Matrix Reloaded"))
                .andExpect(jsonPath("$.anoLancamento").value(2003));

        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void deveRejeitarCamposInvalidos() throws Exception {
        mockMvc.perform(post("/filmes").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":" ","genero":"","anoLancamento":1800}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros.length()").value(3));
        mockMvc.perform(post("/filmes").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404ParaFilmeInexistente() throws Exception {
        mockMvc.perform(get("/filmes/999999")).andExpect(status().isNotFound());
        mockMvc.perform(delete("/filmes/999999")).andExpect(status().isNotFound());
        mockMvc.perform(put("/filmes/999999").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"titulo":"Matrix","genero":"Ação","anoLancamento":1999}
                                """))
                .andExpect(status().isNotFound());
    }
}
