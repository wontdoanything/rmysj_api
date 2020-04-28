package org.rmysj.api.commons.dao.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class MapperXmlNamePlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable table) {
		super.initialized(table);

		table.setMyBatis3JavaMapperType(table.getMyBatis3JavaMapperType()
				.replaceAll("Mapper", ""));
	}
}
