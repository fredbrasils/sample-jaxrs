package br.com.spread.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.spread.model.Person;
import br.com.spread.model.Skill;
import br.com.spread.repository.PersonRepository;

@Service
public class PersonServiceImpl extends BaseServiceImpl<Person, Long> implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	public JpaRepository<Person, Long> getRepository() {
		return personRepository;
	}

	/*
	 * Get all person from database
	 * */
	@Override
	public List<Person> findAll() {
		List<Person> persons = personRepository.findAll();

		for (Person p : persons) {
			// to resolve lazy initialization on JAXB
			p.setSkills(new HashSet<Skill>());
		}

		return persons;
	}

	/*
	 * Get all entity with skills from database
	 * */
	@Override
	public List<Person> findAllWithSkills() {
		return personRepository.findAllWithSkills();
	}

	/*
	 * Get entity with skills from database by id
	 * */
	@Override
	public Person findWithSkillsById(Long id) {
		return personRepository.findWithSkillsById(id);
	}

}
