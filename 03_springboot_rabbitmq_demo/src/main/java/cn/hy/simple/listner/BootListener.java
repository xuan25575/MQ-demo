package cn.hy.simple.listner;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class BootListener {


    // header 注解可以获取 header 的信息
    @RabbitListener(queues = "q.boot")
    public void handleMsg(String message, @Header(value = "hello") String curStr) {
        System.out.println("消息队列推送来的消息:" + message);
        System.out.println(" header v : " +curStr);
    }


}
