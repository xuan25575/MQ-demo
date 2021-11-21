package cn.hy.haproxy.controller;

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
public class BizController {


    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("send/{hello}")
    public String biz(@PathVariable(value = "hello") String hello ) {

        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setContentType("text/plain")
                .setContentEncoding("utf-8").build();
        Message message = MessageBuilder.withBody(hello.getBytes(StandardCharsets.UTF_8))
                        .andProperties(messageProperties).build();
        amqpTemplate.send("ex.haproxy","key.haproxy",message);
        return "ok";
    }


}
