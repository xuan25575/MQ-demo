package cn.hy.simple.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/demo01")
public class Demo01Controller {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {

        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("UTF-8")
                .setHeader("hello","world")
                .build();

        Message msg = MessageBuilder
                .withBody(message.getBytes(StandardCharsets.UTF_8))
                .andProperties(messageProperties)
                .build();
        rabbitTemplate.convertAndSend("ex.boot", "key.boot", msg);
        return "ok";
    }

    //http://localhost:8080/demo01/send
    @RequestMapping("/send")
    public String sendMessage() {
        return "ok";
    }
}
