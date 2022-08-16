package cn.abellee.cniface.platform.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author abel
 * @date 2022/8/14 12:54 PM
 */
@Configuration
public class MilvusClientConfiguration {

    @Bean
    public MilvusServiceClient milvusServiceClient(MilvusClientProperties milvusClientProperties) {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost(milvusClientProperties.getHost())
                .withPort(milvusClientProperties.getPort())
                .withAuthorization(milvusClientProperties.getUsername(), milvusClientProperties.getPassword())
                .build();
        return new MilvusServiceClient(connectParam);
    }
}
