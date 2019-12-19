package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.rpc.client.DiagnoseServiceClient;

@Configuration
public class RPCConfig {
    @Bean
    public DiagnoseServiceClient diagnoseServiceClient() {
        return new DiagnoseServiceClient();
    }
}
