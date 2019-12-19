package top.itcat.rpc.thrift;

import org.apache.thrift.TServiceClient;

public class ThriftClient<T extends TServiceClient> {
    private T client;
    private String host;

    public ThriftClient(T client, String host) {
        this.client = client;
        this.host = host;
    }

    public T getClient() {
        return client;
    }

    public String getHost() {
        return host;
    }
}
