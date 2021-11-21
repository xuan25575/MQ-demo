package cn.hy.Demo04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 主题 topic
 *
 * 三点要求：
 *  点分单词
 *  * 任意一个（单个）
 *  # 0个或者多个（0-多个）
 *
 *
 */
public class MyProducer04 {

    private final static String[] LOG_LEVEL = {"info","error","debug"};

    public static void main(String[] args) throws Exception {

        Random random = new Random();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.10.129:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("ex.demo04","topic",false,false,null);

        for (int i = 0; i < 10; i++) {
            String routingKey = "*."+ LOG_LEVEL[random.nextInt(3)] ;
            channel.basicPublish("ex.demo04",routingKey,null,("hello topic" + i).getBytes(StandardCharsets.UTF_8));
        }


        channel.close();
        connection.close();

    }
}
