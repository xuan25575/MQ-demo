package cn.hy.advance.controller;

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
public class DelayedController {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("send/{message}")
    public String sendTtl(@PathVariable(value = "message") String message) {
        // 必须发送带过期属性的参数
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentEncoding("utf-8")
                .setExpiration("1000").build();
        Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .andProperties(messageProperties).build();
        amqpTemplate.convertAndSend("ex.ttl.demo", "key.ttl.demo", msg);
        return "ok";
    }
}
