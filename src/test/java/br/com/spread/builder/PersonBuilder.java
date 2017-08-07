package br.com.spread.builder;

import java.util.HashSet;

import br.com.spread.model.Person;
import br.com.spread.model.Skill;

public class PersonBuilder {

	private Person person;
	
	public PersonBuilder() {
		this.person = new Person();
	}
	
	public PersonBuilder withId(Long id){
		person.setId(id);
		return this;
	}
	
	public PersonBuilder withFirstName(String firstName){
		person.setFirstName(firstName);
		return this;
	}

	public PersonBuilder withLastName(String lastName){
		person.setLastName(lastName);
		return this;
	}
	
	public PersonBuilder withLinkedinUrl(String linkedinUrl){
		person.setLinkedinUrl(linkedinUrl);
		return this;
	}
	
	public PersonBuilder withWhatsapp(Long whatsapp){
		person.setWhatsapp(whatsapp);
		return this;
	}
	
	public PersonBuilder withMail(String mail){
		person.setMail(mail);
		return this;
	}
	
	public PersonBuilder addSkills(Skill... skills){
		
		if(person.getSkills() == null){
			person.setSkills(new HashSet<Skill>());
		}
		
		for(Skill skill : skills){
			person.getSkills().add(skill);
		}
		
		return this;
	}
	
	public Person build(){
		return person;
	}
}
