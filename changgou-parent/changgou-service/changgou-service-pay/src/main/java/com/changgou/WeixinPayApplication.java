package com.changgou;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @Author: Deng
 * @date: 2020-05-21 19:30
 * @description:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class WeixinPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixinPayApplication.class, args);
    }

    @Autowired
    private Environment environment;

    //创建队列
    @Bean
    public Queue queueOrder() {
        return new Queue(environment.getProperty("mq.pay.queue.order"));
    }

    //创建交换机
    @Bean
    public DirectExchange createExchange() {
        return new DirectExchange(environment.getProperty("mq.pay.exchange.order"));
    }

    //绑定交换机
    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(queueOrder()).to(createExchange()).with(environment.getProperty("mq.pay.routing.key"));
    }
}