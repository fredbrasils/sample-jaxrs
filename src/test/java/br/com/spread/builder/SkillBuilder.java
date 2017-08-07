package br.com.spread.builder;

import br.com.spread.model.Skill;

public class SkillBuilder {

	private Skill skill;
	
	public SkillBuilder() {
		this.skill = new Skill();
	}
	
	public SkillBuilder withId(Long id){
		skill.setId(id);
		return this;
	}
	
	public SkillBuilder withTag(String tag){
		skill.setTag(tag);
		return this;
	}

	public SkillBuilder withDescription(String description){
		skill.setDescription(description);
		return this;
	}
	
	public Skill build(){
		return skill;
	}
}
