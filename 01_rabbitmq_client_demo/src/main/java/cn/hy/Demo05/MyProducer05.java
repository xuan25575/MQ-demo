package cn.hy.Demo05;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ttl
 */
public class MyProducer05 {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@192.168.10.129:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

//        Map<String,Object> arguments = new HashMap<>();
//        // 设置队列的TTL
//        arguments.put("x-message-ttl","10000");
//        // 设置队列的空闲存活时间(如该队列根本没有消费者，一直没有使用，队列可以存活多久)
//        arguments.put("x-expires","30000");
//
//        channel.queueDeclare("q.ttl.demo05",false,false,false,arguments);

        for (int i = 0; i < 10; i++) {
            channel.basicPublish("ex.ttl.demo","q.ttl.demo05",
                    new AMQP.BasicProperties().builder().expiration("5000").build(),
                    ("ttl-message"+ i).getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        connection.close();

    }
}
