# Viridis Dev FullStack Java/React
Seguem abaixo instruções para execução da aplicação localmente.

---

### Pré-requisitos
Deverá ter instalado previamente: Maven, JDK 8, NojeJs, PostgreSQL 9.4 (com usuário e senha padrão: [postgres/postgres])

---

### Iniciando a aplicação
Backend:
Dentro da pasta "Viridis\backend" existe o arquivo "mvnw.bat" que contém os comandos para baixar as dependências e compilar a aplicação Java.
Executar este arquivo.

Frontend:
Dentro da pasta "Viridis\frontend" existe o arquivo "run.bat" que contém os comandos para baixar os pacotes e libs React, e subir um servidor NodeJs com a aplicação React.
Executar este arquivo.

### Acessando a aplicação
Acessar no browser (preferencialmente o Chrome) o endereço http://localhost:3000/

São gerados dois usuários em memória quando a aplicação Java subir:
1. Usuário: admin / Senha: 123 -> este login tem permissão de acesso liberada para acessar toda API REST
1. Usuário: guest / Senha: 123 -> este login não tem permissão em nenhum endpoint da API REST (apenas para demonstração da segurança ativa)

Entre com o usuário "admin" para usar as telas de cadastro.

### Documentação da API REST
Acessa a URL http://localhost:8080/swagger-ui.html
