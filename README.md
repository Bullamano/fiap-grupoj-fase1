# fiap-grupoj-fase1
Repositório para a atividade da Fase 1 (entrega 3) do Grupo J da FIAP.

## Sobre a atividade
A atividade realizada neste repositório é a de criar uma base de dados relacional para armazenar os registros que futuramente serão manipulados pela aplicação cujo protótipo está sendo devolvido agora. Juntamente com a base de dados, deve ser criada uma camada de persistência de dados em Java usando JPA. Por hora, pode ser criada uma estrutura/classe para testar os métodos de persistência de dados. Podem ser usados ainda os bancos de dados relacionais Oracle, MySQL ou Postgres, em suas versões mais recentes, preferencialmente.

## Detalhes do projeto
O projeto é de uma aplicação para ajudar com tarefas corriqueiras. Usuários poderão criar páginas para deixar suas dicas e tutoriais sobre assuntos que os interessem. Também poderão separar estas páginas em categorias, para organizá-las. Dentro dessas páginas poderão haver tanto texto (os "procedimentos") quanto links para imagens, outros textos e vídeos (os "recursos").

Neste projeto, estamos usando Java 17, JPA e banco de dados MySQL. O schema do banco (que está com o nome provisório da aplicação de "Need Help App") se encontra abaixo:

<p align="center">
  <img src="https://i.ibb.co/JpqyYd6/Need-Help-App-DBschema.png" alt="NeedHelpAppSchema"/>
</p>

## Como rodar o projeto
Na pasta "dcScripts", exitem alguns scripts MySQL para criação do banco que será usado. Basta rodá-los em ordem de acordo com os índices em seus nomes e será criado o banco de dados inicial para os testes.
Em seguida, basta rodar os arquivos Java localizados em "fiap-grupoj-fase1/src/main/java/br/com/fiap/needhelpapp/tests/" para realizar os testes de persistência.
Boa sorte e bons testes.

<p align="center">
  <img src="https://steamuserimages-a.akamaihd.net/ugc/1322320103330848025/77B883CDD2640BF75C2B98AF0CD061817A9230DC/"/>
</p>
