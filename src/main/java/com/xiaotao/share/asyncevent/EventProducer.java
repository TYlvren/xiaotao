package com.xiaotao.share.asyncevent;

import com.alibaba.fastjson.JSONObject;
import com.xiaotao.share.asyncevent.enumeration.EventType;
import com.xiaotao.share.asyncevent.enumeration.TargetType;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 生产一个事件，存放到Redis中
 */
public class EventProducer {

    public static void createEvent(Jedis jedis, EventType eventType, int activeId, int targetId,
                                   String targetUri, TargetType targetType, Map<String,Object> extraData){
        Event event = new Event();

        event.setEVENTTYPE(eventType);
        event.setActiveId(activeId);
        event.setTargetId(targetId);
        event.setTargetType(targetType);
        event.setExtraData(extraData);
        event.setTargetUri(targetUri);

        //将事件转换成json字符串存入Redis
        String jsonString = JSONObject.toJSONString(event);
        jedis.lpush("event",jsonString);
        jedis.close();
    }

}
