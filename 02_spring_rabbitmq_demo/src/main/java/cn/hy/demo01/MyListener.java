package cn.hy.demo01;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MyListener {

    //@RabbitListener用于注册Listener时使用的信息：如queue，exchange，key、
    @RabbitListener(queues = "q.spring02")
    //@RabbitListener 和 @RabbitHandler结合使用，不同类型的消息使用不同的方法来处理。
    //@RabbitHandler
    public void handleMsg(Message message){



        System.out.println("msg:"+ new String(message.getBody(), StandardCharsets.UTF_8));
    }
}
