package cn.hy.demo01;

import cn.hy.simple.config.RabbitmqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.StandardCharsets;


/**
 * 容器启动
 */
public class App01 {


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitmqConfig.class);

        AmqpTemplate template = context.getBean(AmqpTemplate.class);


        // 没有声明交换机 默认的交换机，routing key是队列名称
        template.convertAndSend("q.spring01","hello q.spring01");
        Message receive = template.receive("q.spring01");
        System.out.println(new String(receive.getBody(), StandardCharsets.UTF_8));

        // 发送给指定的交换机
        template.convertAndSend("ex.spring02","key.spring02","hello q.spring02");

    }
}
