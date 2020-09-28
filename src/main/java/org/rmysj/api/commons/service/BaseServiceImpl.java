package org.rmysj.api.commons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.rmysj.api.commons.aspect.annotation.CriteriaCondition;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.domain.AbstractCriteria;
import org.rmysj.api.commons.domain.AbstractEntity;
import org.rmysj.api.commons.util.DateUtils;
import org.rmysj.api.commons.util.IdGen;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.rmysj.api.commons.util.Reflections;
import org.rmysj.api.config.Glob;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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

	private static final String[] dateParam = new String[]{"queryDate"};



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

	@Resource
	protected Validator validator;

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseService.class);

	protected  void invokeWhereParams (Object c, JSONObject param){
		this.invokeWhereParams(c,param,dateParam);
	}

	/**
	 *
	 * @param c
	 * @param param
	 * @param dateParam
	 */
	protected  void invokeWhereParams (Object c, JSONObject param,String[] dateParam){
		invokeWhereParams(c,param,dateParam,null);
	}

	/**
	 *
	 * @param c javabean的Criteria
	 * @param param json参数
	 * @param dateParam 自定义时间段查询字段
	 *
	 */
	protected <T> void invokeWhereParams (Object c, JSONObject param,String[] dateParam,Class<T> cls,String... ignores){
		try {
			T object = null;
			if(cls != null) {
				object = JSONObject.parseObject(param.toJSONString(),cls);
			}
			Iterator<String> iterator =  param.getInnerMap().keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				Object value = param.getOrDefault(key,null);
				List<String> ignoreList = ignores != null ? Arrays.asList(ignores) : null;
				if(org.rmysj.api.commons.util.StringUtils.isNotBlank(value)
						&& (ignoreList == null || !ignoreList.contains(value))) {
					if("DATETIME".equalsIgnoreCase(key)) {
						if(StringUtils.isNotBlank(param.getString(key))) {
							Date[] pubDates = DateUtils.getDate2(param.getJSONArray(key));
							for(int i = 0 ; i < dateParam.length;i++) {
								System.out.println(param.getString(dateParam[i]));
								String method = "and" + StringUtils.capitalize(dateParam[i]) + "Between";
								Reflections.invokeMethod(c,method,new Class[]{pubDates[0].getClass(),pubDates[1].getClass()},new Object[]{pubDates[0],pubDates[1]});
							}
						}
					}else {
						String method = "and" + StringUtils.capitalize(key) + "EqualTo";
						if(cls != null) {
							CriteriaCondition aspect = Reflections.getAccessibleFieldAspect(object,key, CriteriaCondition.class);
							if(aspect!= null && aspect.isLike()) {
								method = "and" + StringUtils.capitalize(key) + "Like";
								value = "%" + value + "%";
							}
						}
						Reflections.invokeMethod(c,method,new Class[]{value.getClass()},new Object[]{value});
					}
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e.getCause());
		}
	}

	/**
	 * 拷贝查询条件；仅将原Criteria中的condition复制到目标Criteria，不会覆盖目标Criteria已有condition
	 *
	 * @param source 原Criteria
	 * @param target 目标Criteria
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static <T> void copyCondition(T source, T target)
	{
		if (null != source && null != target)
		{
			try
			{
				Field field = source.getClass().getSuperclass().getDeclaredField("criteria");
				field.setAccessible(true);

				List sourceCriteria = (List) field.get(source);
				List targetCriteria = (List) field.get(target);

				targetCriteria.addAll(sourceCriteria);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 存储文件
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 * @see
	 */
	protected String saveUploadFile(String perfixPath, MultipartFile file) throws Exception
	{
		// 1存储图片,存储在外部目录
		if (file.isEmpty())
		{
			logger.info("文件为空");
		}
		else
		{
			logger.info("有文件!!!!!!");
			String fileName = file.getOriginalFilename();
			String suffixName = org.rmysj.api.commons.util.StringUtils.getExtension(file);
			String saveRemoteFileName = org.rmysj.api.commons.util.StringUtils.encodingFilename(fileName);
			String saveRemotefilePath = "/" +  DateUtils.datePath() + "/" + saveRemoteFileName + "." + suffixName;
			File dest = new File(perfixPath + saveRemotefilePath);
			if (!dest.getParentFile().exists())
			{
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);

			return perfixPath.substring(Glob.getProfile().length()) + saveRemotefilePath;
		}

		// 2同步
		return "";
		// DuoyuanService.sycWXSportImg();
	}

	protected List<String> saveUploadFile(String perfixPath,MultipartFile[] files) throws Exception
	{
		List<String> saveResultList = Lists.newArrayList();
		for(int i = 0; i < files.length; i++) {
			saveResultList.add(this.saveUploadFile(perfixPath,files[i]));
		}
		return saveResultList;
	}

}
