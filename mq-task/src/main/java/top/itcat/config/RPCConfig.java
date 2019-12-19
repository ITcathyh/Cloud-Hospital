package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.rpc.client.OrderServiceClient;

@Configuration
public class RPCConfig {
    @Bean
    public OrderServiceClient orderServiceClient() {
        return new OrderServiceClient();
    }
}
