package br.com.spread.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spread.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{

}
