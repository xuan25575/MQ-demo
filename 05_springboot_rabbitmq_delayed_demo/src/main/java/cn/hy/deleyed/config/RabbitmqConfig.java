package cn.hy.deleyed.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {


    @Bean
    public Queue delayedQueue() {
        Queue queue = new Queue("q.delayed",false,false,false,null);
        return queue;
    }

    @Bean
    public Exchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        // 这是自定义的类型
        args.put("x-delayed-type", "direct");
        // type x-delayed-message
        Exchange exchange  = new CustomExchange("ex.delayed","x-delayed-message",true,false,args);
        return exchange;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayedQueue()).to(delayedExchange()).with("key.delayed").noargs();
    }

}
