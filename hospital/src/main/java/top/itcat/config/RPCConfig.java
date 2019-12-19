package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;

@Configuration
public class RPCConfig {
    @Bean
    public OrderServiceClient orderServiceClient() {
        return new OrderServiceClient();
    }

    @Bean
    public UserServiceClient userServiceClient() {
        return new UserServiceClient();
    }

    @Bean
    public DiagnoseServiceClient diagnoseServiceClient() {
        return new DiagnoseServiceClient(5);
    }

}
