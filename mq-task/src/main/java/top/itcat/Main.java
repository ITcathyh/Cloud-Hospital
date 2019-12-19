package top.itcat;

import org.apache.zookeeper.KeeperException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.itcat.rpc.Clients;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        new Clients().init();
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
    }
}
