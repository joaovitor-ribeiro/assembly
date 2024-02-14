# Assembly

API para votação em pautas de assembleia 

## Pré requistos 

Conter o docker instalado. Obter o docker no site: https://www.docker.com/get-started/ 

Conter o MongoDB instalado. Obter o MongoDB no site: https://www.mongodb.com/try/download/community

Conter o java versão 17. Obter o java no site: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Conter o lombok. Obter o lombok no site: https://projectlombok.org/download

Conter uma IDE de sua preferência.

## Instalação

Clonar o projeto

```bash
  git clone https://github.com/joaovitor-ribeiro/assembly.git
```
Importar o projeto como projeto maven na IDE de sua preferência

Abrir a pasta docker do projeto e executar o comando abaixo no cmd 

```bash
    docker-compose up
```

## Swagger

Link para acesso do Swagger: http://localhost:8080/swagger-ui/index.html#/

## Rodando os testes

Para rodar os testes unitários, execute o package service com junit.

Para rodar os testes de API, execute o package api com junit.
