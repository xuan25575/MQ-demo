package cn.hy.demo01;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyConsumer01 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.129");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("q.hello",false,false,false,null);


        channel.basicConsume("q.hello",true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("received :" + msg + " ||  consumerTag: " + consumerTag);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("consumerTag" + consumerTag);
            }
        });

        //channel.close();
        //connection.close();


    }
}
