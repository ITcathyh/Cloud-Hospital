package top.itcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.itcat.rpc.client.*;

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
        return new DiagnoseServiceClient();
    }

    @Bean
    public HospitalServiceClient hospitalServiceClient() {
        return new HospitalServiceClient();
    }

    @Bean
    public MedicalServiceClient medicalServiceClient() {
        return new MedicalServiceClient();
    }
}
