package org.rmysj.api.commons.domain;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;

public class Global implements EnvironmentAware{
	public static String CODE = "code";
	
	public static String FILE_BASE_URL = "";
	
	public static Long QUICKAPPLY_POINT_FMTLONG = 5L;
	
	public static Long ACTIVITYPUBLISH_POINT_FMTLONG = 10L;
	
	public static  BigDecimal    RMB_SUB_POINT = BigDecimal.TEN;

	public static String CACHE_UNITKILOMETER = "api_unitkilometer_";

	@Override
	public void setEnvironment(Environment environment) {
		FILE_BASE_URL = environment.getProperty("base.fileUrl");
		
	}
}
