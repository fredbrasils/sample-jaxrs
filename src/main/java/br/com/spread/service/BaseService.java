package br.com.spread.service;

import java.util.List;

import org.springframework.http.HttpStatus;

public interface BaseService<T> {

	public List<T> findAll();

	public T save(T entity);

	public T get(Long id);

	public HttpStatus delete(Long id);

	public HttpStatus deleteAll();

	public HttpStatus update(T entity);

}
