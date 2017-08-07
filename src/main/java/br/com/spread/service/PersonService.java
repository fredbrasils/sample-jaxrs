package br.com.spread.service;

import java.util.List;

import br.com.spread.model.Person;

public interface PersonService extends BaseService<Person> {

	public List<Person> findAllWithSkills();

	public Person findWithSkillsById(Long id);
}
