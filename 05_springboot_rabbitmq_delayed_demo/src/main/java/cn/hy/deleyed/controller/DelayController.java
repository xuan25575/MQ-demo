package cn.hy.deleyed.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
public class DelayController {


    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("send/{time}")
    public String delayMsg(@PathVariable(value = "time") Integer time) {
        MessageProperties messageProperties = new MessageProperties();
        // 通过属性控制延迟 时间
        messageProperties.setHeader("x-delay",(time - 5) * 1000);
        Message message = new Message((time+"秒后开会！").getBytes(StandardCharsets.UTF_8), messageProperties);
        amqpTemplate.convertAndSend("ex.delayed","key.delayed",message);
        return "ok";
    }


}
