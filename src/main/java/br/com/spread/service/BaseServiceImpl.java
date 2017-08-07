package br.com.spread.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.spread.model.AbstractModel;

@Service
public class BaseServiceImpl<T extends AbstractModel<Long>, Long extends Serializable> implements BaseService<T> {

	private JpaRepository<T, Long> repository;

	public JpaRepository<T, Long> getRepository() {
		return repository;
	}

	/*
	 * Get all entity from database
	 * */
	public List<T> findAll() {
		return getRepository().findAll();
	}

	/*
	 * Save entity
	 * */
	public T save(T entity) {
		return getRepository().save(entity);
	}

	/*
	 * Get entity by id
	 * */
	public T get(java.lang.Long id) {
		T entity = getRepository().findOne((Long) id);
		return entity;
	}

	/*
	 * Delete entity by id
	 * */
	public HttpStatus delete(java.lang.Long id) {
		try {
			getRepository().delete((Long) id);
			return HttpStatus.OK;
		} catch (EmptyResultDataAccessException e) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/*
	 * Delete all entity from database
	 * */
	public HttpStatus deleteAll() {
		try {
			List<T> list = findAll();
			getRepository().delete(list);
			return HttpStatus.OK;
		} catch (EmptyResultDataAccessException e) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/*
	 * Update entity
	 * */
	public HttpStatus update(T entity) {
		T getEntity = getRepository().findOne(entity.getId());
		getRepository().save(entity);
		return HttpStatus.OK;
	}

}
