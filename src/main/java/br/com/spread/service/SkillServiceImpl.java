package br.com.spread.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.spread.model.Skill;
import br.com.spread.repository.SkillRepository;

@Service
public class SkillServiceImpl extends BaseServiceImpl<Skill, Long>{
  
  @Autowired
  private SkillRepository skillRepository;

  public JpaRepository<Skill, Long> getRepository() {
    return skillRepository;
  }
  
}
