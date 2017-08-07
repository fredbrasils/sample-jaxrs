package br.com.spread.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

import br.com.spread.builder.SkillBuilder;
import br.com.spread.model.Skill;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SkillControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static Skill skill1 = new SkillBuilder()
			.withTag("Java")
			.withDescription("Java Skill")
			.build();
	
	private static Skill skill2 = new SkillBuilder()
			.withTag("Angular")
			.withDescription("Angular Skill")
			.build();
	
	private static Skill skill3 = new SkillBuilder()
			.withTag("Spring")
			.withDescription("Spring Skill")
			.build();
	
	private List<Skill> listSkill = new ArrayList<Skill>(){{
		add(skill1);add(skill2);add(skill3);
	}};
	
	@Before
	public void startSkillTest(){
		// insert all skill
		for(Skill skill : listSkill){
			saveSkill(skill);
		}
	}
	
	@After
	public void endSkillTest(){
		// remove all skill
		removeAllSkill();
	}
	
	@Test
	public void createSkill() {
		Skill skill = new SkillBuilder()
				.withTag("Ruby")
				.withDescription("Ruby Skill")
				.build();
		
		ResponseEntity<Skill> entity = saveSkill(skill);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		listSkill.add(skill);
	}
	
	@Test
	public void returnAllSkill(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		assertThat(entities.getBody().length).isEqualTo(3);
	}
	
	@Test
	public void returnSkillById(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		Long id = entities.getBody()[0].getId();
		
		Skill entity = getSkillById(id);
		assertThat(entity.getId()).isEqualTo(id);
	}
	
	@Test
	public void updateSkillById(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		Long id = entities.getBody()[0].getId();
		
		Skill skill = getSkillById(id);
		assertThat(skill.getId()).isEqualTo(id);
		
		skill.setTag("Oracle");
		skill.setDescription("Oracle Skill");
		
		HttpEntity<Skill> request = new HttpEntity<>(skill);
		this.restTemplate.put("/skill/"+id,request);
		
		skill = getSkillById(id);
		assertThat(skill.getTag()).isEqualTo("Oracle");
		assertThat(skill.getDescription()).isEqualTo("Oracle Skill");
		
	}
	
	@Test
	public void updateSkillByDescription(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		Long id = entities.getBody()[0].getId();
		
		Skill skill = getSkillById(id);
		assertThat(skill.getId()).isEqualTo(id);
		skill.setDescription("ScrumSkill");
		
		HttpEntity<Skill> request = new HttpEntity<>(skill);
		this.restTemplate.put("/skill/"+id+"/description/ScrumSkill",request);
		
		skill = getSkillById(id);
		assertThat(skill.getDescription()).isEqualTo("ScrumSkill");
	}
	
	@Test
	public void deleteSkillById(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		Long id = entities.getBody()[0].getId();
		
		Skill entity = getSkillById(id);
		assertThat(entity.getId()).isEqualTo(id);
		
		deleteSkill(id);
		entity = getSkillById(id);
		assertThat(entity).isNull();
	}
	
	@Test
	public void deleteAllSkill(){
		ResponseEntity<Skill[]> entities = getAllSkill();
		assertThat(entities.getBody().length > 0).isEqualTo(true);
		
		removeAllSkill();
		
		entities = getAllSkill();
		assertThat(entities.getBody()).isNullOrEmpty();
		
	}
	
	/*Get specific skill*/
	public Skill getSkillById(Long id){
		return this.restTemplate.getForObject("/skill/"+id,Skill.class);
	}
	
	/*Ger all skill*/
	private ResponseEntity<Skill[]> getAllSkill(){
		return this.restTemplate.getForEntity("/skill",Skill[].class);
	}

	/* Save skill */
	private ResponseEntity<Skill> saveSkill(Skill skill){
		HttpEntity<Skill> request = new HttpEntity<>(skill);
		return this.restTemplate.postForEntity("/skill",request, Skill.class);
	}
	
	/*Delete all skill */
	private void removeAllSkill(){
		deleteSkill(null);
	}
	
	/* Delete skill by id or all skill*/
	private void deleteSkill(Long id){
		if(id != null){
			this.restTemplate.delete("/skill/"+id);
		}else{
			this.restTemplate.delete("/skill");
		}
	}
}
