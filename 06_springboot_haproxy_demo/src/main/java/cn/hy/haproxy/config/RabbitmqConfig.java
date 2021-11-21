package cn.hy.haproxy.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {


    @Bean
    public Queue delayedQueue() {
        Queue queue = new Queue("q.haproxy",true,false,false,null);
        return queue;
    }

    @Bean
    public Exchange delayedExchange() {
        Exchange exchange  = new DirectExchange("ex.haproxy",true,false,null);
        return exchange;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayedQueue()).to(delayedExchange()).with("key.haproxy").noargs();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

}
