package cn.abellee.cniface.platform.milvus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author abel
 * @date 2022/8/14 12:51 PM
 */
@ConfigurationProperties("milvus.client")
@Configuration
@Data
public class MilvusClientProperties {

    private String host = "127.0.0.1";

    private Integer port = 19530;

    private String username = "root";

    private String password = "Milvus";
}
