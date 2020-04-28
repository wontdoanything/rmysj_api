package org.rmysj.api.commons.service;

import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.domain.AbstractCriteria;
import org.rmysj.api.commons.domain.AbstractEntity;
import org.rmysj.api.commons.util.IdGen;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;*/

@Transactional
public abstract class BaseServiceImpl<T extends AbstractEntity, E extends AbstractCriteria>
		implements BaseService<T, E> {

	protected Logger log = Logger.getLogger(getClass());

	public static String STATUS = "status";

	public static String OK = "200";
	
	public static String CREATED = "201";
	
	public static String AUTHFAIL = "202";
	
	public static String WARN = "900";
	
	public static String DESC = "desc";


	
	protected abstract BaseDao<T, E, String> getDao();

	protected Class<T> entityClazz;

	protected Class<E> criteriaClazz;



	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClazz = (Class<T>) params[0];
		criteriaClazz = (Class<E>) params[1];
	}

	@Override
	public T create(T entity) {
		Assert.notNull(entity);
		String id = entity.getId() == null ? IdGen.uuid() : entity.getId()
				.trim();
		entity.setId(id);
		Date now = new Date();
		if(entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		if(entity.getUpdateDate() == null) {
			entity.setUpdateDate(now);
		}
		getDao().insertSelective(entity);
		return entity;
	}

	@Override
	public List<T> create(List<T> entities) {
		Assert.notEmpty(entities);
		List<T> list = new ArrayList<T>();
		for (T t : entities) {
			list.add(create(t));
		}
		return list;
	}

	@Override
	public T update(T entity) {
		Assert.notNull(entity);
		T existing = getDao().selectByPrimaryKey(entity.getId());
		BeanUtils.copyProperties(entity, existing);
		existing.setUpdateDate(new Date());
		getDao().updateByPrimaryKeySelective(existing);
		return findOne(entity.getId());
	}

	@Override
	public List<T> update(List<T> entities) {
		Assert.notEmpty(entities);
		List<T> list = new ArrayList<T>();
		for (T t : entities) {
			list.add(update(t));
		}
		return list;
	}

	@Override
	public T save(T entity) {
		Assert.notNull(entity);
		if (entity.getId() == null) {
			create(entity);
		} else {
			update(entity);
		}
		return findOne(entity.getId());
	}

	@Override
	public List<T> save(List<T> entities) {
		Assert.notEmpty(entities);
		List<T> list = new ArrayList<T>();
		for (T t : entities) {
			list.add(save(t));
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public T findOne(String id) {
		Assert.notNull(id);
		return getDao().selectByPrimaryKey(id);
	}

	@Override
	public boolean exists(String id) {
		Assert.notNull(id);
		return findOne(id) != null;
	}

	@Override
	public long count() {
		return getDao().countByExample(null);
	}
	
	@Override
	public long count(E criteria) {
		Assert.notNull(criteria);
		return getDao().countByExample(criteria);
	}

	@Override
	public void delete(String id) {
		Assert.notNull(id);
		getDao().deleteByPrimaryKey(id);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity);
		getDao().deleteByPrimaryKey(entity.getId());
	}

	@Override
	public void delete(List<T> entities) {
		for (T t : entities) {
			delete(t);
		}
	}

	@Override
	public void delete(E criteria) {
		Assert.notNull(criteria);
		getDao().deleteByExample(criteria);
	}

	@Override
	public void deleteAll() {
		delete(findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return getDao().selectByExample(null);
	}

	@Override
	@Transactional(readOnly =true)
	public List<T> search(E criteria){
		Assert.notNull(criteria);
		return getDao().selectByExample(criteria);
	}


	
	/*@Override
	@Transactional(readOnly = true)
	public List<T> findAll(Sort sort) throws Exception {
		Assert.notNull(sort);
		String orderByClause = "";
		for (Order o : sort) {
			orderByClause += " " + o.getProperty() + " "
					+ o.getDirection().toString() + " ";
		}
		E criteria = criteriaClazz.newInstance();
		criteria.setOrderByClause(orderByClause);
		return getDao().selectByExample(criteria);
	}*/

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) throws Exception {
		Assert.notNull(pageable);
		E criteria = null;
		if (pageable.getSort() != null) {
			String orderByClause = "";
			for (Order o : pageable.getSort()) {
				orderByClause += " " + o.getProperty() + " "
						+ o.getDirection().toString() + " ";
			}
			criteria = criteriaClazz.newInstance();
			criteria.setOrderByClause(orderByClause);
		}
		RowBounds rowBounds = new RowBounds(pageable.getPageNumber(),
				pageable.getPageSize());
		List<T> list = getDao().selectByExampleWithRowbounds(criteria,
				rowBounds);
		long count = count();
		return new PageImpl<T>(list, pageable, count);
	}
	
	
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<T> search(E criteria, Sort sort) {
//		Assert.notNull(criteria);
//		Assert.notNull(sort);
//		if (sort != null) {
//			String orderByClause = "";
//			for (Order o : sort) {
//				orderByClause += " " + o.getProperty() + " "
//						+ o.getDirection().toString() + " ";
//			}
//			criteria.setOrderByClause(orderByClause);
//		}
//		return getDao().selectByExample(criteria);
//	}

	@Override
	@Transactional(readOnly = true)
	public com.github.pagehelper.Page<T> search(E criteria, Pageable pageable) {
		Assert.notNull(criteria);
		Assert.notNull(pageable);
		if (pageable.getSort() != null) {
			String orderByClause = "";
			for (Order o : pageable.getSort()) {
				orderByClause += " " + o.getProperty() + " "
						+ o.getDirection().toString() + " ";
			}
			criteria.setOrderByClause(orderByClause);
		}
		RowBounds rowBounds = new RowBounds(pageable.getPageNumber(),
				pageable.getPageSize());
		com.github.pagehelper.Page<T> page = getDao().selectByExampleWithRowbounds(criteria,
				rowBounds);
//		long count = count(criteria);

		return page;
	}

//	@Override
//	@Transactional(readOnly = true)
//	public Page<T> search(E criteria, Pageable pageable) {
//		Assert.notNull(criteria);
//		Assert.notNull(pageable);
//		if (pageable.getSort() != null) {
//			String orderByClause = "";
//			for (Order o : pageable.getSort()) {
//				orderByClause += " " + o.getProperty() + " "
//						+ o.getDirection().toString() + " ";
//			}
//			criteria.setOrderByClause(orderByClause);
//		}
//		RowBounds rowBounds = new RowBounds(pageable.getPageNumber(),
//				pageable.getPageSize());
//		List<T> list = getDao().selectByExampleWithRowbounds(criteria,
//				rowBounds);
//		long count = count(criteria);
//
//		return new PageImpl<T>(list, pageable, count);
//	}

	public static String getStackTraceAsString(Throwable e) {
		if (e == null){
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}



}
