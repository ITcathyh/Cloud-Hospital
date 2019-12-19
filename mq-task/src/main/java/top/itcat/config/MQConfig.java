package top.itcat.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;

@Configuration
public class MQConfig {
    @Bean("orderDelayCheckDestination")
    public Destination orderDelayCheckDestination() {
        return new ActiveMQQueue("order.check.delay");
    }
}
