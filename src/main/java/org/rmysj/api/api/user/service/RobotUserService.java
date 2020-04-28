package org.rmysj.api.api.user.service;

import org.rmysj.api.api.user.domain.RobotUser;
import org.rmysj.api.api.user.domain.RobotUserCriteria;
import org.rmysj.api.commons.service.BaseService;

/**
 * Created by rmysj on 2017/7/28 上午10:25.
 */
public interface RobotUserService extends BaseService<RobotUser,RobotUserCriteria> {

    /**
     * 注册用户，按照用户ID（传给哈工大注册接口的是name字段，name当ID用）
     * user
     */
    RobotUser createByUserId(RobotUser user);
}
