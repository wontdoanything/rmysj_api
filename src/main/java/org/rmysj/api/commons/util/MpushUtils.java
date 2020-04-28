package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpush.api.push.*;
import org.rmysj.api.config.Glob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by rmysj on 2018/1/29 下午4:16.
 */
public class MpushUtils {

    private static Logger logger = LoggerFactory.getLogger(MpushUtils.class);

    private static int packagemode = Integer.valueOf(Glob.getConfig("mpush.api.packagemode")).intValue();

    /**
     *
     * @param jsonObject 要发送的内容
     * @param userId 发送的单个ID,窄播时必填，广播时填null
     * @param warnHisId 发送编号
     * @param condition 发送条件，一般匹配tags
     * @param isBroadcast 是否广播
     * @param userList 接受多个ID集合，只有当userId为Null，采用窄播模式发送时才生效
     */
    public  static void push(JSONObject jsonObject,String userId,String warnHisId,String condition,boolean isBroadcast,List<String> userList){
//        jsonObject.put("title","多维融合防入侵报警系统");

        PushSender sender = PushSender.create();
        boolean sendFlag = sender.isRunning();
        if(!sendFlag) {
            logger.debug("断开状态，重新创建sender");
            sendFlag = sender.start().join();
        }
        if(sendFlag) {
            logger.debug("发送长度：" + jsonObject.toJSONString().length() + ",发送内容为：" + jsonObject.toJSONString());
            PushMsg msg = PushMsg.build(MsgType.MESSAGE, jsonObject.toJSONString());
            PushContext context = PushContext.build(msg)
                    .setAckModel(AckModel.AUTO_ACK)
                    .setUserId(packagemode ==1?userId:"test1")
                    .setTaskId(warnHisId)
                    .setBroadcast(isBroadcast)
                    //.setTags(Sets.newHashSet("test"))
                    .setCondition(condition)
                    .setTimeout(10000)
                    .setCallback(new PushCallback() {
                        @Override
                        public void onResult(PushResult result) {
                            logger.debug(result.toString());
                            System.err.println("\n\n" + result);
                        }
                    });
            if(StringUtils.isBlank(userId) && !isBroadcast && userList != null && !userList.isEmpty()) {
                //非单个用户并且不是广播
                context.setUserIds(userList);
            }
            FutureTask<PushResult> future = sender.send(context);
            logger.debug(future.toString());
            if (future.isDone()) {
                logger.debug("mpush done\n\n" + future);
                System.err.println("\n\n" + future);
            }
            if (future.isCancelled()) {
                logger.debug("mpush cancelled\n\n" + future);
                System.err.println("\n\n" + future);
            }
        }
    }

    /**
     *
     * @param warnHisId 报警记录ID
     * @param userId mpush用USERID
     */
    public static void mpush(String warnHisId, List<Object> pushMsgList, String userId)
    {
//                String warnHisId,String warnDeviceId,String camaraId,String preset,String camaraType,String userId
        JSONObject json = new JSONObject();
        json.put("pushMsgList", JSONArray.parseArray(JSON.toJSONString(pushMsgList)));
        json.put("id",warnHisId);
        json.put("warnstate","1");
        String condition = "tags&&tags.indexOf('" + (packagemode ==1?userId:"test1") + "')!=-1";
        if(userId != null && userId.indexOf("_simple") >= 0) {
            condition = "tags&&tags.indexOf('" + (packagemode ==1?userId:"test1_simple") + "')!=-1";
        }else if (userId != null && userId.indexOf("_ysy") >= 0){
            condition = "tags&&tags.indexOf('" + (packagemode ==1?userId:"test1_ysy") + "')!=-1";
        }
        push(json,userId,warnHisId,condition,true,null);
    }
    
    public static void startPushThread(JSONObject jsonObject,String userId,String warnHisId,String condition,boolean isBroadcast,List<String> userList){
		new Thread(new Runnable() {
			@Override
			public void run() {
	            push(jsonObject, userId, warnHisId, condition, isBroadcast, userList);
//	            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
			}
		}).start();
	}

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("11",22);
        push(json, "user-0", "123123123", "", false, null);
    }
}
