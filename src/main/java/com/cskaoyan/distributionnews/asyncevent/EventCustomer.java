package com.cskaoyan.distributionnews.asyncevent;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.distributionnews.asyncevent.enumeration.EventType;
import com.cskaoyan.distributionnews.eventhandler.EventHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;

@Component
public class EventCustomer implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 事件注册表Map，key为类型，value为事件处理器的List，根据类型获取处理这些类型的事件处理器
     */
    private Map<EventType, List<EventHandler>> eventRegisterTable = new HashMap<>();

    @Autowired
    private Jedis jedis;

    //Bean初始化的时候调用

    /**
     * 初始化事件处理器注册表，
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //获取所有的处理器
        Map<String, EventHandler> handlerMap = applicationContext.getBeansOfType(EventHandler.class);
        Collection<EventHandler> handlers = handlerMap.values();


        //初始化事件注册表
        for (EventHandler handler : handlers) {
            //获取处理器的处理事件类型
            List<EventType> eventTypes = handler.getHandleEventType();


            //遍历处理器的事件类型
            for (EventType eventType : eventTypes) {

                //如果事件类型已经注册,将处理器加入该类型的List中
                if(eventRegisterTable.containsKey(eventType)){
                    List<EventHandler> eventHandlers = eventRegisterTable.get(eventType);
                    eventHandlers.add(handler);

                }else {//事件类型第一次注册，将事件类型作为key添加进注册表
                    List<EventHandler> eventHandlers = new ArrayList<>();
                    eventHandlers.add(handler);
                    eventRegisterTable.put(eventType,eventHandlers);
                }
            }
        }


        //从Redis中的list中取事件，如果list中没有事件则进行阻塞，避免整个应用阻塞，启用子线程
        new Thread(){
            @Override
            public void run() {

                while (true) {
                    //使用Redis的阻塞方法取出队列中的事件
                    //第一个元素是list的key，第二个元素是值
                    String eventJson = jedis.brpop(60 * 60, "event").get(1);

                    //将json解析为event对象
                    Event event = JSONObject.parseObject(eventJson, Event.class);

                    //根据事件类型，将类型分发给相应的处理器
                    List<EventHandler> eventHandlers = eventRegisterTable.get(event.getEVENTTYPE());

                    for (EventHandler eventHandler : eventHandlers) {
                        eventHandler.handleEvent(event);
                    }
                }
            }
        }.start();

    }

    //获取application容器
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
