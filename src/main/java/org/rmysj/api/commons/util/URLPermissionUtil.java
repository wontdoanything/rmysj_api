package org.rmysj.api.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 规定各个URL的访问权限.
 * @Description:TODO
 * @author rmysj
 * @date 2018年11月28日 下午3:29:36
 * @version 1.0
 *
 */
public class URLPermissionUtil{
	public final static String PERMISSION = "1";
	public static Map<String/*需要拦截验证权限的url */,String/*1、表示需要校验权限*/> map = new HashMap<String,String>();
	static{
		map.put("/api/index/test", PERMISSION);
		map.put("/api/getTeamList", PERMISSION);
		map.put("/api/getOfficeList", PERMISSION);
		map.put("/api/getUserListByOfficeId", PERMISSION);
		
		map.put("/api/user/loginOut", PERMISSION);	
		map.put("/api/create_team", PERMISSION);	
		map.put("/api/dismiss_team", PERMISSION);	
		map.put("/api/add_user2team", PERMISSION);	
		map.put("/api/moify_team", PERMISSION);	
//		map.put("/api/check_version", PERMISSION);	
		map.put("/api/user_info", PERMISSION);	
		map.put("/api/get_msg_list", PERMISSION);	
		map.put("/api/sign_msg", PERMISSION);
		map.put("/api/upload_gis", PERMISSION);
		map.put("/api/upload/post", PERMISSION);
		map.put("/api/add_event", PERMISSION);
		map.put("/api/getGisByComp", PERMISSION);

		/**
		 * START TalkController
		 */
		map.put("/api/checkUserBeforeTrans", PERMISSION);
		map.put("/api/checkUserBeforeTransWebRtc", PERMISSION);
		map.put("/api/checkTeamBeforeTransRtc", PERMISSION);
		map.put("/api/checkTeamBeforeTrans", PERMISSION);
		map.put("/api/talkTapDown", PERMISSION);
		map.put("/api/talkTapUp", PERMISSION);
		map.put("/api/transNotify", PERMISSION);
		map.put("/api/transFinish", PERMISSION);
		map.put("/api/getlastRecByTeam", PERMISSION);
		map.put("/api/onDvrWebRtc", PERMISSION);
		/**
		 * END TalkController
		 */

		/**
		 * START IMController
		 */
//		map.put("/api/rtc/eventCenter", PERMISSION);
		/**
		 * END IMController
		 */

		/**
		 * START SDK相关
		 */
		map.put("/api/luShuRecord", PERMISSION);
		/**
		 * END SDK相关
		 */

	}
}
