package cn.hy.deleyed.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MyListener {


    @RabbitListener(queues = "q.delayed")
    public void handleMsg(Message message, Channel channel) throws Exception {
        System.out.println("提醒 5秒后 ："+ new String(message.getBody(), StandardCharsets.UTF_8));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
