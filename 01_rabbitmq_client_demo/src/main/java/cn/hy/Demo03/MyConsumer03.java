package cn.hy.Demo03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyConsumer03 {
    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.129");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare("ex.demo03","direct",false,false,null);
        channel.queueDeclare("q.demo03",false,false,false,null);
        channel.queueBind("q.demo03","ex.demo03","key.demo03");


        channel.basicConsume("q.demo03", true,new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("received : " + s);
                //System.out.println("tag: "+ consumerTag);
            }
        },consumerTag -> {});


    }
}
