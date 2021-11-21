package cn.hy.advance.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ttl 对列配合 死信队列 可以完成 延迟功能
 *
 * 变成死信 的条件
 * 1.消息被拒绝(Basic.Reject/Basic.Nack)，并且设置requeue参数为false;
 * 2.消息过期;
 * 3.队列达到最大长度
 */
@Configuration
public class RabbitmqConfig {


    //手动配置SimpleRabbitListenerContainerFactory
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
//        containerFactory.setConnectionFactory(connectionFactory);
//        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        return containerFactory;
//    }



    // -----------------------ttl exchange queue ..---------------------------
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 30 * 1000);
        arguments.put("x-expires", 60 * 1000);
        // 绑定死信属性
        arguments.put("x-dead-letter-exchange","ex.dlx.demo");
        arguments.put("x-dead-letter-routing-key","key.dlx.demo");

        Queue queue = new Queue("q.ttl.demo", false, false, false, arguments);
        return queue;
    }

    @Bean
    public Exchange ttlExchange(){
        Exchange exchange = new DirectExchange("ex.ttl.demo",false,false,null);
        return exchange;
    }


    @Bean
    public Binding ttlBinding(@Qualifier(value = "ttlQueue") Queue queue,@Qualifier(value = "ttlExchange") Exchange exchange) {
        return  BindingBuilder.bind(queue).to(exchange).with("key.ttl.demo").noargs();
    }


    // -----------------------dead exchange queue ..---------------------------

    @Bean
    public Queue dlxQueue() {
       Queue queue =  new Queue("q.dlx.demo",false,false,false,null);
       return  queue;
    }

    @Bean
    public Exchange dlxExchange() {
        Exchange exchange = new DirectExchange("ex.dlx.demo",false,false,null);
        return exchange;
    }

    @Bean
    public Binding dlxBinding() {
        Binding binding = new Binding("q.dlx.demo",
                Binding.DestinationType.QUEUE,"ex.dlx.demo",
                "key.dlx.demo",null);
        return binding;
    }




}