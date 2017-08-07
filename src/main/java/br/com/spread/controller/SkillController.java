package br.com.spread.controller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.spread.model.Person;
import br.com.spread.model.Skill;
import br.com.spread.service.BaseServiceImpl;
import br.com.spread.service.PersonServiceImpl;
import br.com.spread.service.SkillServiceImpl;

@Component
@Path("skill")
public class SkillController extends BaseController<Skill,Long> {
	
	@Autowired
	private SkillServiceImpl skillService;

	@Autowired
	private PersonServiceImpl personService;
	
	@Override
	public BaseServiceImpl<Skill, Long> getService() {
		return skillService;
	}
	

	/*
	 * Method to save the entity skill
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Skill skill) {

		// verify if exist object skill
		if (skill == null) {
			return Response.noContent().build();
		} else {
			
			// This block get all the person associated to skill
			Set<Person> persons = new HashSet<Person>();
			if(skill.getPersons() != null){
				for(Person person : skill.getPersons()){
					if(person.getId() != null){
						persons.add(personService.get(skill.getId()));
					}
					persons.add(person);
				}
				skill.setPersons(null);
			}
			
			// save skill without persons
			skill = getService().save(skill);
			skill.setPersons(persons);
			
			// save skill with persons
			getService().save(skill);

			// verify if exist id skill
			if (skill.getId() != null) {
				URI uri = URI.create("skill/" + skill.getId());
				return Response.created(uri).build();
			} else {
				return Response.noContent().build();
			}
		}
	}
	
	/*
	 * Update description of specific skill
	 * 
	 * */
	@Path("{id}/description/{description}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePersonName(Skill skill, @PathParam("id") Long id,@PathParam("description") String description) {
		
		// verify if exist skill
		if (skill == null) {
			return Response.noContent().build();
		} else {

			// get skill from database
			Skill skillSaved = getService().get(skill.getId());
			// set new description
			skillSaved.setDescription(description);
			// update skill
			getService().update(skillSaved);
			
			// get updated skill from database
			skillSaved = getService().get(skill.getId());
			
			// verify if the description was updated
			if (skillSaved.getDescription().equals(description)) {
				return Response.ok().build();
			} else {
				return Response.notModified().build();
			}
		}
    }
	

	/*
	 * Delete all skill from database
	 * */
	@DELETE
	public Response deleteAll() {
		
		/*This block return all skill from database, saved them with
		 * person's list null after remove them from database*/
		try{
			List<Skill> skills = getService().findAll();
			for(Skill skill : skills){
				skill.setPersons(null);
				skill = getService().save(skill);
				getService().delete(skill.getId());
			}
			
			return Response.ok().build();
			
		}catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	/*
	 * Delete skill from database
	 * */
	@Path("{id}")
	@DELETE
	public Response delete(@PathParam("id") Long id) {

		/*This block return skill from database, saved it with
		 * person's list null after remove it from database*/
		try{
			Skill skill = getService().get(id);
			skill.setPersons(null);
			skill = getService().save(skill);
			getService().delete(skill.getId());
			
			return Response.ok().build();
			
		}catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
