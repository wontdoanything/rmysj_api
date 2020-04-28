package org.rmysj.api.commons.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable, E> {
	T create(T entity);

	List<T> create(List<T> enetities);

	T update(T entity);

	List<T> update(List<T> entities);

	T save(T entity);

	List<T> save(List<T> entities);

	T findOne(String id);

	boolean exists(String id);

	long count();

	void delete(String id);

	void delete(T entity);

	void delete(List<T> entities);

	void deleteAll();

	List<T> findAll();
	
	List<T> search(E criteria);

	void delete(E criteria);
/*	
	List<T> findAll(Sort sort) throws Exception;
*/
	Page<T> findAll(Pageable pageable) throws Exception;
/*	
	List<T> search(E criteria, Sort sort);
*/
	com.github.pagehelper.Page<T> search(E criteria, Pageable pageable);

	long count(E criteria);

}
