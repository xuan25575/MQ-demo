package cn.hy.demo02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OneConsumer02 {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory  =new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.10.129:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.demo02","fanout",
                true,
                false,
                null);
        String queueName = channel.queueDeclare().getQueue(); // 生成一个默认的queue
        System.out.println(" 临时队列 queueName: " + queueName);
        // fanout 类型不需要 routing-key
        channel.queueBind(queueName,"ex.demo02","");

        channel.basicConsume(queueName, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("One received: " + s );
            }
        },consumerTag -> {});


        //channel.close();
        //conn.close();

    }
}
