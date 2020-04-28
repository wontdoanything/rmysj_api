package org.rmysj.api.commons.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends Serializable ,E, ID extends Serializable> {
	int countByExample(E example);
	
	int deleteByExample(E example);
	
	int deleteByPrimaryKey(ID id);
	
	int insert(T record);
	
	int insertSelective(T record);
	
	Page<T> selectByExampleWithRowbounds(E example, RowBounds rowBounds);
	
	List<T> selectByExample(E example);
	
	T selectByPrimaryKey(ID id);
	
	int updateByExampleSelective(@Param("record") T record,@Param("example") E example);
	
	int updateByExample(@Param("record") T record,@Param("example") E example);
	
	int updateByPrimaryKeySelective(T record);
	
	int updateByPrimaryKey(T record);
}
