# README #

### (Português)
Esse projeto é um CRUD de Pessoas e Habilidades (Person and Skill) utilizando Spring-boot,
Jersey (como implementação do JAX-RS), HSQLDB em memória e Maven.

### Como rodar?

#### Etapas:
	1. Baixe o projeto.
	2. Abra o prompt. 
	3. Entre com o comando: cd jax-rs-sample
	4. Digite o comando: mvn spring-boot:run para executar o serviço

#### Como usar os servicos ?  

Eu utilizei o [Postman](https://www.getpostman.com/) para fazer os testes.  

As URLs para Person são:  
	* http://localhost:8080/person (POST, GET, DELETE)  
	* http://localhost:8080/person/{id} (GET, PUT, DELETE)  
	* http://localhost:8080/person/skills (GET)  
	* http://localhost:8080/person/{id}/skills (GET)  
	* http://localhost:8080/person/{id}/firstName/{firstName} (PUT)    

As URLs para Skill são:  
	* http://localhost:8080/skill (POST, GET, DELETE)
	* http://localhost:8080/skill/{id} (GET, PUT, DELETE)
	* http://localhost:8080/skill/{id}/description/{description} (PUT)

 Exemplo JSON Person:  
  {
   "id": 1,	
   "firstName": "Pedro",
   "lastName": "silva",
   "linkedinUrl": "www.linkedin.com/pedro",
   "whatsapp": 99995555,
   "mail": "pedro@gmail.com",
   "skills":[
 		{
 		  "tag": "PHP",
 		  "description": "PHP Skill"
 		},
 		{
 		  "tag": "Kanban",
 		  "description": "Kanban Skill"
 		}
    ]
   } 
	
 Exemplo JSON Skill:  
   {
 		{
 		  "tag": "PHP",
 		  "description": "PHP Skill"
 		}
   } 
	
### Como rodar os teste?

#### Etapas:
	1. Baixe o projeto.
	2. Abra o prompt 
	3. Entre com o comando: cd jax-rs-sample
	4. Digite o comando: mvn test para executar o teste

### Problemas ?

* Tive problemas com o Lazy Initialization com o JAXB com as coleções. Tive que setar as listas como null para não dar problema na resposta. Ainda não encontrei a melhor maneira pra resolver esse problema.
	
### Como quem falar?
	
	email: fredbrasils@hotmail
	
### (English) ###

This project is a CRUD of Person and Skill.
This project use Spring-boot, Jersey (JAX-RS implementation), HSQLDB memory and Maven.	

### How do I get set up? ###

#### Steps: #### 
	* 1 - Download project.
	* 2 - Open prompt command. 
	* 3 - Execute the command: cd jax-rs-sample
	* 4 - Enter: mvn spring-boot:run to execute the service

#### How I use the service ? #### 

* I used Postman (https://www.getpostman.com/) to call the services.

* URLs (Person) are:
** http://localhost:8080/person (POST, GET, DELETE)
** http://localhost:8080/person/{id} (GET, PUT, DELETE)
** http://localhost:8080/person/skills (GET)
** http://localhost:8080/person/{id}/skills (GET)
** http://localhost:8080/person/{id}/firstName/{firstName} (PUT)

* URLs (Skill) are:
** http://localhost:8080/skill (POST, GET, DELETE)
** http://localhost:8080/skill/{id} (GET, PUT, DELETE)
** http://localhost:8080/skill/{id}/description/{description} (PUT)

* Example JSON Person:
* {
*   "id": 1,	
*   "firstName": "Pedro",
*   "lastName": "silva",
*   "linkedinUrl": "www.linkedin.com/pedro",
*   "whatsapp": 99995555,
*   "mail": "pedro@gmail.com",
*   "skills":[
* 		{
* 		  "tag": "PHP",
* 		  "description": "PHP Skill"
* 		},
* 		{
* 		  "tag": "Kanban",
* 		  "description": "Kanban Skill"
* 		}
*  ]
* } 
	
* Example JSON Skill:
* {
* 		{
* 		  "tag": "PHP",
* 		  "description": "PHP Skill"
* 		}
* } 


### How do I test? ###

#### Steps: #### 
	* 1 - Download project.
	* 2 - Open prompt command. 
	* 3 - Execute the command: cd jax-rs-sample
	* 4 - Enter: mvn test to execute the tests
	
### Problem ? ###

* Lazy Initialization with JAXB collections. 

### Who do I talk to? ###

	email: fredbrasils@hotmail.com