package cn.hy.demo02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class MyProduct02 {
    public static void main(String[] args) throws Exception {

        ConnectionFactory factory  =new ConnectionFactory();
        //协议：用户 密码@ip:port/虚拟主机   %2f ==> / 默认的虚拟主机
        factory.setUri("amqp://root:123456@192.168.10.129:5672/%2f");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();


        channel.exchangeDeclare("ex.demo02","fanout",false,false,null);

        //channel.queueDeclare("q.demo02",false,false,false,null);


        for (int i = 0; i < 10; i++) {
            channel.basicPublish("ex.demo02","",null,("helle fanout" + i).getBytes(StandardCharsets.UTF_8));

        }


        channel.close();
        conn.close();
    }
}
