package cn.hy.simple.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 基于注解方式
 */
@Configuration
//@EnableRabbit和@Configuration一起使用，可以加在类或者方法上，这个注解开启了容器对注册的bean的@RabbitListener检查。
@EnableRabbit
@ComponentScan(value = "cn.hy.demo01")
public class RabbitmqConfig {


    @Bean
    public com.rabbitmq.client.ConnectionFactory rabbitFactory(){
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost("192.168.10.129");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory(com.rabbitmq.client.ConnectionFactory rabbitFactory) {
        //CachingConnectionFactory 是Spring 的实现
       ConnectionFactory factory = new CachingConnectionFactory(rabbitFactory);
        return factory;
    }

    /**
     * AMQP 协议的一个实现
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


    /**
     * 使用 @RabbitListener  需要配置
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        //还可以设置其他参数
        return containerFactory;
    }


    @Bean
    public Queue springQ1() {
        Queue queue = new Queue("q.spring01", false,false,false,null);
        return queue;
    }


    @Bean
    public Exchange exchange(){
        Exchange exchange = ExchangeBuilder.directExchange("ex.spring02").durable(false).build();
        return exchange;
    }

    @Bean
    public Queue springQ2() {
        Queue queue = QueueBuilder.durable("q.spring02").build();
        return queue;
    }


    @Bean
    public Binding binding(@Qualifier("springQ2") Queue queue,@Qualifier("exchange") Exchange exchange) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("key.spring02").noargs();
        return binding;
    }




}
