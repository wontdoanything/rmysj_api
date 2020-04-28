package org.rmysj.api.api.jsonpController;

import com.google.common.collect.Maps;
import org.rmysj.api.api.user.domain.RobotUser;
import org.rmysj.api.api.user.service.RobotUserService;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.service.redis.IRedisService;
import org.rmysj.api.commons.util.MD5andKL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by rmysj on 2017/7/20 下午5:07.
 */
@RestController
@RequestMapping(value = "/api")
public class RobotUserController extends BaseController{


    @Autowired
    private IRedisService redisService;


    @Autowired
    private RobotUserService userService;

    private static final String CACHE_PERFIX = "rmysj";

    private static final String CACHE_LAST_COMMER = "last_commer";



    /**
     * 查找注册用户信息
     * @param user
     * @param md5
     * @param response
     * @return
     */
    @RequestMapping(value = "/searchUser",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUser(RobotUser user,
                                        @RequestParam(value = "sign", required = true) String md5,
                                        HttpServletResponse response){
        log.debug("REST request to /api.rmysj.ser/searchUser : " +
                "{ user:"+user+",md5:"+md5+" }"  );
        Map<String,Object> map = Maps.newHashMap();
        map.put(STATUS,OK);
        if(StringUtils.isBlank(user.getId())
                || StringUtils.isBlank(md5)) {
            map.put(STATUS,WARN);
            map.put(DESC,"params error");
        }else{
            String checkSign = MD5andKL.MD5(user.getId());
            if (checkSign.equals(md5)) {
                RobotUser rmysj = userService.findOne(user.getId());
                if (rmysj != null) {
                    map.put(STATUS,OK);
                    map.put("rmysj",rmysj);
                }else{
                    map.put(STATUS,ACCOUNT_NO_ACTIVE);
                    map.put(DESC,"find no user");
                }
            }else {
                map.put(STATUS,WARN);
                map.put(DESC,"sign error");
            }

        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * 查找注册用户信息
     * @param user
     * @param md5
     * @param response
     * @return
     */
    @RequestMapping(value = "/registerUser",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(RobotUser user,
                                        @RequestParam(value = "sign", required = true) String md5,
                                        HttpServletResponse response){
        log.debug("REST request to /api.rmysj.ser/registerUser : " +
                "{ user:"+user+",md5:"+md5+" }"  );
        Map<String,Object> map = Maps.newHashMap();
        map.put(STATUS,OK);
        if(StringUtils.isBlank(user.getId())
                || StringUtils.isBlank(user.getUserSex())
                || StringUtils.isBlank(user.getUserFace())
                || user.getUserAge() == null
                || user.getUserAttractive() == null
                || StringUtils.isBlank(user.getEyeGlass())
                || StringUtils.isBlank(user.getSunGlass())
                || user.getSmile() == null
                || StringUtils.isBlank(user.getEmotion())
                || StringUtils.isBlank(md5)) {
            map.put(STATUS,WARN);
            map.put(DESC,"params error");
        }else{
            String checkSign = MD5andKL.MD5(user.getId() + user.getUserSex() + user.getUserFace()
                                    + user.getUserAge() + user.getUserAttractive() + user.getEyeGlass()
                                    + user.getSunGlass() + user.getSmile() + user.getEmotion());
            if (checkSign.equals(md5)) {
                RobotUser rmysj = userService.findOne(user.getId());
                if (rmysj == null) {
                    rmysj =  userService.createByUserId(user);
                    map.put(STATUS,OK);
                    map.put("user" ,rmysj);
                }else{
                    map.put(STATUS,WARN);
                    map.put(DESC,"user has register");
                }
            }else {
                map.put(STATUS,WARN);
                map.put(DESC,"sign error");
            }
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
