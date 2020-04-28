package org.rmysj.api.api.user.service.impl;

import org.rmysj.api.api.user.dao.RobotUserMapper;
import org.rmysj.api.api.user.domain.RobotUser;
import org.rmysj.api.api.user.domain.RobotUserCriteria;
import org.rmysj.api.api.user.service.RobotUserService;
import org.rmysj.api.commons.dao.BaseDao;
import org.rmysj.api.commons.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by rmysj on 2017/7/28 上午10:26.
 */
@Service
@Transactional(readOnly = true)
public class RobotUserServiceImpl extends BaseServiceImpl<RobotUser,RobotUserCriteria> implements RobotUserService {

    @Autowired
    private RobotUserMapper robotUserDao;

    @Value("${base.filePath}")
    private String basePath;
    @Value("${base.perfix1filePath}")
    private String perfix1;
    @Value("${base.perfix2filePath}")
    private String perfix2;

    @Override
    protected BaseDao<RobotUser, RobotUserCriteria, String> getDao() {
        return robotUserDao;
    }

    /**
     * 注册用户，按照用户ID（传给哈工大注册接口的是name字段，name当ID用）
     * user
     *
     * @param user
     */
    @Override
    @Transactional(readOnly = false)
    public RobotUser createByUserId(RobotUser user) {
        Date now = new Date();
        user.setCreateDate(now);
        user.setUpdateDate(now);

        String imgFilePath =   user.getId() + ".png";
        if (generateImage(user.getUserFace(), basePath, perfix2 + imgFilePath)){
            user.setUserFace(perfix1 + perfix2 + imgFilePath);
            robotUserDao.insertSelective(user);
            return robotUserDao.selectByPrimaryKey(user.getId());
        }else {
            return null;
        }

    }

    private boolean generateImage(String imgStr,String basePath ,String imgFilePath) {
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(basePath + imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
