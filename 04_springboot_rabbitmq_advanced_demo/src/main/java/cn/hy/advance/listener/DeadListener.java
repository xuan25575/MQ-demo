package cn.hy.advance.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class DeadListener {


    @RabbitListener(queues = "q.dlx.demo")
    public void handleMsg(Message message, Channel channel) throws IOException {
        System.out.println("收到死信：" + new String(message.getBody(), StandardCharsets.UTF_8));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
