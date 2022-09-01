package cn.itsource.config.rabbit;

import cn.itsource.constants.BaseConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 声明交换机、队列、以及绑定关系
 * @auth: tangrufei
 * @date: 2022-08-24 10:15
 */
@Component
public class RabbitMQConfig {

    public static final String DEFAULT_EXCHANGE = "default_exchange_0907";
    public static final String ROUTING_KEY = "#.email";

    //声明交换机
    @Bean
    private Exchange defaultExchange(){
        return ExchangeBuilder.topicExchange(DEFAULT_EXCHANGE).durable(true).build();
    }

    //声明队列
    @Bean
    private Queue emailQueue(){
        return new Queue(BaseConstants.RabbitMQConstants.EMAIL_QUEUE, true, false, false);
    }

    //交换机和队列的绑定
    @Bean
    private Binding defaultBinding(){
        return BindingBuilder.bind(emailQueue()).to(defaultExchange()).with(ROUTING_KEY).noargs();
    }
}