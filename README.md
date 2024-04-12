# Informações Úteis

## Sobre o projeto

### Arquitetura e Solução

Esse é um microsserviço desenvolvido utilizando Clean Architecture. Foi criado a API realizar a leitura do arquivo e
salvar no banco de dados (MongoDB) e também foi criada a API para fazer a consulta do pedido, retornando um JSON para
o usuário, podendo incluir filtros de id do pedido e data inicial/data final. Existem logs por todo o código para ajudar
a acompanhar o fluxo de um requisição. Por fim, foram criados testes unitários para garantir a funciolidade e uma maior
qualidade.

### Tecnologias utilizadas

* Java 17 e Spring Boot
* JUnit e Mockito
* MongoDB
* Lombok
* MapStruct
* Swagger para mapeamento das APIs

## Guia para rodar o projeto

### Itens necessários

* JDK 17 instalado e configurado corretamente no seu ambiente.
* Docker instalado e configura corretamente no seu ambiente.
    * Executar o docker-compose.yml disponível para subir um MongoDB local
* Plugin do Lombok instalado e configurado:
    * Preferences (Ctrl + Alt + S)
    * Build, Execution, Deployment
    * Compiler
    * Annotation Processors
    * Enable annotation processing

### Sobre a aplicação

Vídeo explicativo: https://www.youtube.com/watch?v=yjSBwWk5UkE

Compilação e Testes

* Abra um terminal na raiz do projeto.
* Execute o comando mvn clean install para compilar o projeto e executar os testes unitários.

Configuração no IntelliJ IDEA

* Abra o IntelliJ IDEA.
* No canto superior direito, clique em Current File.
* Selecione Edit Configurations....
* Clique em Add new run configuration e escolha Application.
* Na nova configuração de aplicação, certifique-se de selecionar o JDK 17.
* No campo Main class, insira com.luizalabs.logistica.LogisticaApplication.
* Salve as configurações.

Executando a Aplicação

* Com as configurações devidamente configuradas, você pode executar a aplicação diretamente do IntelliJ IDEA.
* Certifique-se de que todas as dependências foram baixadas e que não há erros de compilação.
* Certifique-se também que o container do docker do MongoDB está rodando.
* Inicie a aplicação e acompanhe a saída no console para verificar se tudo está funcionando corretamente.
* Link do swagger para executar as APIs: http://localhost:8080/swagger-ui/index.html
