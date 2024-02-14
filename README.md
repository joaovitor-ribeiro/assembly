# Assembly

Projeto para gerenciar sessões de votações em pautas de assembleia atráves de uma API REST. 

## Tecnologias
Java 17

Spring Boot

MongoDB

Docker

Kafka

## Pré requistos 

Conter o GitBash instalado. Obter o GitGBash no site: https://git-scm.com/downloads

Conter o Docker instalado. Obter o Docker no site: https://www.docker.com/get-started/

Caso acorra problema com a instalação do Docker. Visualizar documentação: https://docs.docker.com/desktop/troubleshoot/topics/

Conter o MongoDB instalado. Obter o MongoDB no site: https://www.mongodb.com/try/download/community

Conter o Java versão 17. Obter o Java no site: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Conter uma IDE de sua preferência.

Conter o Lombok de acordo com sua IDE. Obter o Lombok no site: https://projectlombok.org/

## Instalação

Caso utilize o Eclipse executar o lombok e instalar.

Clonar o projeto

```bash
  git clone https://github.com/joaovitor-ribeiro/assembly.git
```
Importar o projeto como projeto maven na IDE de sua preferência.

Executar o maven update.

Abrir a pasta docker do projeto e executar o comando abaixo no cmd 

```bash
    docker-compose up
```

Executar a classe AssemblyApplication como Java Application 

## Swagger

Link para acesso do Swagger: http://localhost:8080/swagger-ui/index.html#/

## Rodando os testes

Para rodar os testes unitários, execute o package service com junit.

Para rodar os testes de API, execute o package api com junit.

## Versão

O projeto se encontra na versão 1, contrado atráves da URL das requisições. Caso seja feita uma nova versão a URL das requisições será atualizada. 
