package br.com.spread.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.spread.builder.PersonBuilder;
import br.com.spread.builder.SkillBuilder;
import br.com.spread.model.Person;
import br.com.spread.model.Skill;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static Skill skill1 = new SkillBuilder()
			.withTag("JavaScript")
			.withDescription("JavaScript Skill")
			.build();
	
	private static Skill skill2 = new SkillBuilder()
				.withTag("Angular 2")
				.withDescription("Angular 2 Skill")
				.build();
	
	private static Person person1 = new PersonBuilder()
			.withFirstName("Joao")
			.withLastName("Santos")
			.withMail("joao.santos@gmail.com")
			.withLinkedinUrl("http://linkedin.com/joaosantos")
			.withWhatsapp(99998888l)
			.build();
	
	private static Person person2 = new PersonBuilder()
			.withFirstName("Pedro")
			.withLastName("Silva")
			.withMail("pedro.silva@gmail.com")
			.withLinkedinUrl("http://linkedin.com/pedrosilva")
			.withWhatsapp(99998887l)
			.addSkills(skill1)
			.build();
	
	private static Person person3 = new PersonBuilder()
			.withFirstName("Maria")
			.withLastName("Rosa")
			.withMail("maria.rosa@gmail.com")
			.withLinkedinUrl("http://linkedin.com/mariarosa")
			.withWhatsapp(99998886l)
			.addSkills(skill1,skill2)
			.build();
	
	

	private List<Person> listPerson = new ArrayList<Person>(){{
		add(person1);add(person2);add(person3);
	}};
	
	@Before
	public void startPersonTest(){
		
		// insert all person
		for(Person person : listPerson){
			savePerson(person);
		}
	}
	
	@After
	public void endPersonTest(){
		// remove all person
		removeAllPerson();
	}
	
	
	@Test
	public void createPerson() {
		Person person = new PersonBuilder()
					.withFirstName("Fred")
					.withLastName("Brasil")
					.withMail("fred.brasil@gmail.com")
					.withLinkedinUrl("http://linkedin.com/fredbrasil")
					.withWhatsapp(99998885l)
					.build();
		
		ResponseEntity<Person> entity = savePerson(person);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		listPerson.add(person);
	}
	
	@Test
	public void createPersonWithSkill() {
			
		Person person = new PersonBuilder()
					.withFirstName("Fred")
					.withLastName("Brasil")
					.withMail("fred.brasil@gmail.com")
					.withLinkedinUrl("http://linkedin.com/fredbrasil")
					.withWhatsapp(99998885l)
					.addSkills(skill1,skill2)
					.build();
		
		ResponseEntity<Person> entity = savePerson(person);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		listPerson.add(person);
	}
	
	@Test
	public void returnAllPerson(){
		ResponseEntity<Person[]> entities = getAllPerson();
		assertThat(entities.getBody().length).isEqualTo(3);
	}
	
	@Test
	public void returnAllPersonWithoutSkills(){
		ResponseEntity<Person[]> persons = getAllPerson();
		assertThat(persons.getBody().length).isEqualTo(3);
		
		for(Person p : persons.getBody()){
			assertThat(p.getSkills()).isNullOrEmpty();
		}
	}
	
	@Test
	public void returnAllPersonWithSkills(){
		ResponseEntity<Person[]> persons = this.restTemplate.getForEntity("/person/skills",Person[].class);
		assertThat(persons.getBody().length).isEqualTo(3);
		
		assertThat(persons.getBody()[2].getSkills()).isNotNull();
		assertThat(persons.getBody()[2].getSkills()).isNotEmpty();
	}
	
	@Test
	public void returnPersonById(){
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[0].getId();
		
		Person entity = getPersonById(id);
		assertThat(entity.getId()).isEqualTo(id);
	}
	
	@Test
	public void returnPersonWithSkillsById(){
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[2].getId();
		
		Person person = this.restTemplate.getForObject("/person/"+id+"/skills",Person.class);
		assertThat(person.getId()).isEqualTo(id);
		assertThat(person.getSkills()).isNotNull();
		assertThat(person.getSkills()).isNotEmpty();
	}
	
	@Test
	public void updatePersonById(){
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[0].getId();
		
		Person person = getPersonById(id);
		assertThat(person.getId()).isEqualTo(id);
		
		person.setFirstName("Francisco");
		person.setLastName("Souza");
		person.setMail("francisco.souza@gmail.com");
		
		HttpEntity<Person> request = new HttpEntity<>(person);
		this.restTemplate.put("/person/"+id,request);
		
		person = getPersonById(id);
		assertThat(person.getFirstName()).isEqualTo("Francisco");
		assertThat(person.getLastName()).isEqualTo("Souza");
		assertThat(person.getMail()).isEqualTo("francisco.souza@gmail.com");
		
	}
	
	@Test
	public void updatePersonWithSkillsById(){
		
		Skill skill = new SkillBuilder()
				.withTag("HTML")
				.withDescription("HTML Skill")
				.build();
		
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[2].getId();
		
		Person person = getPersonById(id);
		assertThat(person.getId()).isEqualTo(id);
		
		HashSet<Skill> skills = new HashSet<Skill>(); 
		skills.add(person.getSkills().iterator().next());
		skills.add(skill);
		person.setSkills(skills);
		
		HttpEntity<Person> request = new HttpEntity<>(person);
		this.restTemplate.put("/person/"+id,request);
		
		person = getPersonById(id);
		
		boolean finded = false;
		for(Skill sk : person.getSkills()){
			if(sk.getTag().equals(skill.getTag())){
				finded = true;
			}
		}
		
		assertThat(finded).isTrue();
	}
	
	@Test
	public void updatePersonByName(){
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[0].getId();
		
		Person person = getPersonById(id);
		assertThat(person.getId()).isEqualTo(id);
		
		HttpEntity<Person> request = new HttpEntity<>(person);
		this.restTemplate.put("/person/"+id+"/firstName/Francisco",request);
		
		person = getPersonById(id);
		assertThat(person.getFirstName()).isEqualTo("Francisco");
	}
	
	@Test
	public void deletePersonById(){
		ResponseEntity<Person[]> entities = getAllPerson();
		Long id = entities.getBody()[0].getId();
		
		Person entity = getPersonById(id);
		assertThat(entity.getId()).isEqualTo(id);
		
		deletePerson(id);
		
		entity = getPersonById(id);
		assertThat(entity).isNull();
	}
	
	@Test
	public void deleteAllPerson(){
		ResponseEntity<Person[]> entities = getAllPerson();
		assertThat(entities.getBody().length > 0).isEqualTo(true);
		
		removeAllPerson();
		
		entities = getAllPerson();
		assertThat(entities.getBody()).isNullOrEmpty();
		
	}
	
	/*Get specific person*/
	public Person getPersonById(Long id){
		return this.restTemplate.getForObject("/person/"+id,Person.class);
	}
	
	/*Ger all person*/
	private ResponseEntity<Person[]> getAllPerson(){
		return this.restTemplate.getForEntity("/person",Person[].class);
	}

	/* Save person */
	private ResponseEntity<Person> savePerson(Person person){
		HttpEntity<Person> request = new HttpEntity<>(person);
		return this.restTemplate.postForEntity("/person",request, Person.class);
	}
	
	/*Delete all person */
	private void removeAllPerson(){
		deletePerson(null);
	}
	
	/* Delete person by id or all person*/
	private void deletePerson(Long id){
		if(id != null){
			this.restTemplate.delete("/person/"+id);
		}else{
			this.restTemplate.delete("/person");
		}
	}
	
}
