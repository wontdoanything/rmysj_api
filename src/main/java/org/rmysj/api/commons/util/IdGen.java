package org.rmysj.api.commons.util;

import org.springframework.stereotype.Service;

import javax.ws.rs.ext.ParamConverter.Lazy;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@Lazy
public abstract class IdGen {
	
	@SuppressWarnings("unused")
	private static SecureRandom random = new SecureRandom();
	
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
