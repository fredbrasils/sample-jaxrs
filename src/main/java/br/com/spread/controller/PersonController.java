package br.com.spread.controller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("person")
public class PersonController extends BaseController<Person,Long> {

	@Autowired
	private PersonServiceImpl personService;

	@Autowired
	private SkillServiceImpl skillService;
	
	@Override
	public BaseServiceImpl<Person, Long> getService() {
		return personService;
	}
	
	/*
	 * Method to save the entity person
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Person person) {

		// verify if exist object person
		if (person == null) {
			return Response.noContent().build();
		} else {
			
			// This block get all the skills associated to person
			Set<Skill> skills = new HashSet<Skill>();
			if(person.getSkills() != null){
				for(Skill skill : person.getSkills()){
					if(skill.getId() != null){
						skills.add(skillService.get(skill.getId()));
					}
					skills.add(skill);
				}
				person.setSkills(null);
			}
			
			// save person without skills
			person = getService().save(person);
			person.setSkills(skills);
			
			// save person with skills
			getService().save(person);

			// verify if exist id person
			if (person.getId() != null) {
				URI uri = URI.create("person/" + person.getId());
				return Response.created(uri).build();
			} else {
				return Response.noContent().build();
			}
		}
	}
	
	/*
	 * Get all person
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> findAll() {
		return personService.findAll();
	}

	/*
	 * Get all person with skills
	 * */
	@Path("skills")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> findAllWithSkills() {
		return personService.findAllWithSkills();
	}
	
	/*
	 * Get specific person with your skills
	 * */
	@Path("{id}/skills")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person findWithSkillsById(@PathParam("id") Long id) {
		return personService.findWithSkillsById(id);
	}
	
	/*
	 * Update first name of specific person
	 * 
	 * */
	@Path("{id}/firstName/{firstName}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePersonName(Person person, @PathParam("id") Long id,@PathParam("firstName") String firstName) {
		
		// verify if exist person
		if (person == null) {
			return Response.noContent().build();
		} else {

			// get person from database
			Person personSaved = getService().get(person.getId());
			// set new first name
			personSaved.setFirstName(firstName);
			// update person
			getService().update(personSaved);
			
			// get updated person from database
			personSaved = getService().get(person.getId());
			
			// verify if the name was updated
			if (personSaved.getFirstName().equals(firstName)) {
				return Response.ok().build();
			} else {
				return Response.notModified().build();
			}
		}
    }
	
	/*
	 * Delete all person from database
	 * */
	@DELETE
	public Response deleteAll() {
		
		/*This block return all person from database, saved them with
		 * skill's list null after remove them from database*/
		try{
			List<Person> persons = getService().findAll();
			for(Person person : persons){
				person.setSkills(null);
				person = getService().save(person);
				getService().delete(person.getId());
			}
			
			return Response.ok().build();
			
		}catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	/*
	 * Delete person from database
	 * */
	@Path("{id}")
	@DELETE
	public Response delete(@PathParam("id") Long id) {

		/*This block return person from database, saved it with
		 * skill's list null after remove it from database*/
		try{
			Person person = getService().get(id);
			person.setSkills(null);
			person = getService().save(person);
			getService().delete(person.getId());
			
			return Response.ok().build();
			
		}catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
