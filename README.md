# ApiFilme — Cadastro de filmes

Atividade extra em Java com Spring Boot.
Utiliza Gradle, Spring Web MVC, Spring Data JPA, validação e H2,
com uma organização simplificada em domínio, aplicação e infraestrutura.

A classe `Filme` contém `id`, `titulo`, `genero` e `anoLancamento`.
O `FilmeController` disponibiliza as operações REST de cadastro de filmes.
O ID é gerado automaticamente pelo banco.

## Abrir e executar no IntelliJ IDEA

1. Selecione **File > Open** e abra a pasta `ApiFilme`.
2. Importe como projeto Gradle e aguarde a sincronização das dependências.
3. Em **File > Project Structure**, selecione o JDK 25 como Project SDK.
   Em **Settings > Build, Execution, Deployment > Build Tools > Gradle**,
   selecione o mesmo JDK como Gradle JVM e use o Gradle Wrapper do projeto.
4. Abra `src/main/java/com/project/apifilme/ApiFilmeApplication.java`
   e execute o método `main` pelo botão verde.
5. Acesse <http://localhost:8080/filmes> para consultar os filmes.

Requisitos: JDK 25 e acesso à internet no primeiro build.
Spring Boot 4.1.1 e Gradle 9.7.1.

Também é possível executar pelo PowerShell na pasta do projeto:

```powershell
.\gradlew.bat bootRun
```

Para rodar os testes e gerar o JAR:

```powershell
.\gradlew.bat clean build
java -jar .\build\libs\ApiFilme-0.0.1-SNAPSHOT.jar
```

Execute apenas uma instância por vez; a API utiliza a porta 8080 e o banco local.
Para encerrar a execução, use o botão Stop do IntelliJ ou `Ctrl+C` no terminal.

## Desafio extra: três filmes cadastrados

Quando o banco está vazio, a aplicação cadastra automaticamente:

| Título | Gênero | Ano de lançamento |
| --- | --- | --- |
| Interestelar | Ficção científica | 2014 |
| O Poderoso Chefão | Drama | 1972 |
| Toy Story | Animação | 1995 |

Os dados ficam salvos no H2 em `data/filmesdb`, relativo à pasta de execução,
e permanecem após reiniciar. O cadastro inicial só ocorre se o banco estiver vazio.
Os testes usam um banco separado em memória, sem alterar os dados locais.

## Endpoints

| Método | Caminho | Ação | Resposta de sucesso |
| --- | --- | --- | --- |
| GET | `/filmes` | Listar filmes | 200 |
| GET | `/filmes/{id}` | Buscar por ID | 200 |
| POST | `/filmes` | Cadastrar filme | 201 e cabeçalho Location |
| PUT | `/filmes/{id}` | Atualizar filme | 200 |
| DELETE | `/filmes/{id}` | Excluir filme | 204 |

Exemplo de JSON para POST ou PUT:

```json
{
  "titulo": "Matrix",
  "genero": "Ficção científica",
  "anoLancamento": 1999
}
```

Título e gênero são obrigatórios, com limites de 150 e 100 caracteres.
O ano é obrigatório e deve estar entre 1888 e 9999.
Dados inválidos retornam 400; um ID inexistente retorna 404.

O arquivo `filmes.http` permite executar requisições no cliente HTTP do IntelliJ
quando esse recurso estiver disponível na edição instalada.

Se a API estiver executando em segundo plano pela validação local, encerre essa
instância antes de executar pelo IntelliJ:

```powershell
$apiPid = [int](Get-Content .\build\application.pid)
Stop-Process -Id $apiPid
```
