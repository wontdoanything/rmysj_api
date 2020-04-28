package org.rmysj.api.commons.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;



public abstract class AbstractCriteria<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String orderByClause;
	
	private String limitClause; //添加分页条件 String.format("limit %d,%d",pageNo,pageSize);

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	
	public String getLimitClause() {
		return limitClause;
	}

	public void setLimitClause(String limitClause) {
		this.limitClause = limitClause;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T getTargetCriteria(T cia,AbstractAuditingEntity entity,Map<String,String> conditionParam) throws Exception{
		Class clazz = cia.getClass();
		Method method = clazz.getMethod("createCriteria");  
		Object criteria = method.invoke(clazz.newInstance());  
		Class criteriaClass = criteria.getClass();
		Method [] methods = criteriaClass.getDeclaredMethods();
		
		Class entityClass = entity.getClass();
		Field [] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object val = field.get(entity);
			if(val!=null && StringUtils.isNotEmpty(val.toString()) 
					&& StringUtils.isNotEmpty(conditionParam.get(field.getName()))){
				for (Method criteriaMethod : methods) {
					String cmdName = criteriaMethod.getName();
					if(cmdName.equalsIgnoreCase("and"+field.getName()+conditionParam.get(field.getName()))){
						System.out.println(criteriaMethod.getReturnType()+" "+criteriaMethod.getName());
						criteriaMethod.invoke(criteria, val);
					}
				}
			}
		}
		Method orMethod = clazz.getMethod("or",criteria.getClass());
		orMethod.invoke(cia, criteria);
		return cia;
	}
	

}
