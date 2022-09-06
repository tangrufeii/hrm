package cn.itsource.consumer;

import cn.itsource.constants.BaseConstants;
import cn.itsource.dto.EmailDto;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-20 10:53
 */
@Component
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = {BaseConstants.RabbitMQConstants.EMAIL_QUEUE})
    public void handleEmail(String content, Channel channel, Message message) throws IOException, InterruptedException {
        log.info("监听邮件队列，收到消息：{}", content);
        EmailDto dto = JSONObject.parseObject(content, EmailDto.class);
        //业务逻辑：发送邮件，调用网关接口
        List<String> emailAddress = dto.getEmailAddress();
        for (int i = 0; i < emailAddress.size(); i++) {
            log.info("调用第三方接口发送邮件，收件箱={}", emailAddress.get(i));
        }
        Thread.sleep(3000);

        //手动ACK
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
