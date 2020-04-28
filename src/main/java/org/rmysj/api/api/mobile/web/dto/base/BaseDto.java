package org.rmysj.api.api.mobile.web.dto.base;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


@SuppressWarnings("serial")
public abstract class BaseDto<B,T> implements Serializable{
	protected Class<T> dtoClazz;
	
	@SuppressWarnings("unchecked")
	public BaseDto() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		dtoClazz = (Class<T>) params[1];
	}

	/**
	 * 映射参数对象相同字段的值
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年11月27日 上午9:22:09
	 */
	public T convert(B bean) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		T tObj = dtoClazz.newInstance();
		Field [] beanFileds = bean.getClass().getDeclaredFields();
		Field [] dtoFileds = dtoClazz.getDeclaredFields();
		for (Field bField : beanFileds) {
			bField.setAccessible(true);
			for (Field tField : dtoFileds) {
				tField.setAccessible(true);
				if(bField.getName().equals(tField.getName())){
					tField.set(tObj, bField.get(bean));
				}
			}
		}
		return tObj;
	}
	
	/**
	 * 映射参数对象相同字段的值，并以列表的形式返回
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @version 1.0
	 * @author rmysj
	 * @date 2018年11月27日 上午9:24:26
	 */
	public List<T> convert(List<B> bean) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		List<T> tList = Lists.newArrayList();
		for (B b : bean) {
			T tObj = dtoClazz.newInstance();
			Field [] beanFileds = b.getClass().getDeclaredFields();
			Field [] dtoFileds = dtoClazz.getDeclaredFields();
			for (Field bField : beanFileds) {
				bField.setAccessible(true);
				for (Field tField : dtoFileds) {
					tField.setAccessible(true);
					if(bField.getName().equals(tField.getName())){
						tField.set(tObj, bField.get(b));
					}
				}
			}
			tList.add(tObj);
		}
		return tList;
	}
}
