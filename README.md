# Assembly


## Pré requistos 

Conter o docker instalado. Obter o docker no site https://www.docker.com/get-started/ 

Conter o MongoDB instalado. Obter o MongoDB no site https://www.mongodb.com/try/download/community

Conter o java versão 17. Obter o java no site https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

## Instalação

Clonar o projeto

```bash
  git clone https://github.com/joaovitor-ribeiro/controle-financeiro-mongodb.git
```
Importar o controle-financeiro-back como projeto maven na IDE de sua preferência

Abrir a pasta docker do projeto e executar o comando abaixo no cmd 

```bash
    docker-compose up
```

No mongodb criar o database assembly

## Rodando os testes

Para rodar os testes unitários do back, execute o package service com junit.

Para rodar os testes de API do back, execute o package api com junit.
