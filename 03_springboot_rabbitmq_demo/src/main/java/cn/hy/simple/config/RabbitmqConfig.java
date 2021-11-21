package cn.hy.simple.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 不需要配置基础配置了
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue myQueue(){
       Queue queue =  new Queue("q.boot",false,false,false,null);
       return queue;
    }


    @Bean
    public Exchange myExchange(){
       Exchange exchange = new DirectExchange("ex.boot",false,false,null);
       return exchange;
    }


    @Bean
    public Binding myBinding() {
       Binding binding =  new Binding("q.boot",Binding.DestinationType.QUEUE,
                "ex.boot","key.boot", null);
       return binding;
    }
}
