package cn.hy.Demo04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyConsumer04Topic3 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //amqp://root:123456@192.168.10.129:5672/%2f
        //协议：用户 密码@ip:port/虚拟主机   %2f ==> / 默认的虚拟主机
        factory.setUri("amqp://root:123456@192.168.10.129:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.demo04","topic",false,false,null);
        channel.queueDeclare("q.demo04",false,false,false,null);
        channel.queueBind("q.demo04","ex.demo04","*.debug");


        channel.basicConsume("q.demo04", true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("received:" + s) ;
            }
        },consumerTag -> {});


    }
}
