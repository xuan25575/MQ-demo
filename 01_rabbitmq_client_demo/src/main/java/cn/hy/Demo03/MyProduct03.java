package cn.hy.Demo03;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class MyProduct03 {


    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.129");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.demo03", BuiltinExchangeType.DIRECT,false,false,null);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish("ex.demo03","key.demo03",null, ("hello direct" + i).getBytes(StandardCharsets.UTF_8));
        }


        channel.close();
        connection.close();

    }
}
