package cn.hy.demo01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class MyProduct01 {
    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.129");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //队列名称，是否持久化，是否排外，是否自动删除
        channel.queueDeclare("q.hello",false,false,false,null);
        //声明ex
        //channel.exchangeDeclare("ex.hello","fount",false,false,false)

        // 不指定交换机 则会发送到一个默认 直接交换机((AMQP default))， routing-key 默认是 队列名字
        channel.basicPublish("","q.hello",null,"hello".getBytes(StandardCharsets.UTF_8));

        System.out.println("1111");
        channel.close();
        connection.close();



    }
}
