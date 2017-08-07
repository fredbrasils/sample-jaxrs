package br.com.spread.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.spread.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	@Query("select distinct p from Person p left join fetch p.skills")
	List<Person> findAllWithSkills();
	
	@Query("select distinct p from Person p left join fetch p.skills where p.id = ?1")
	Person findWithSkillsById(Long id);
	
}
