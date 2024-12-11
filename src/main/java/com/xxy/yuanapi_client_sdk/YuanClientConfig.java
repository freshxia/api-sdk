package com.xxy.yuanapi_client_sdk;

import com.xxy.yuanapi_client_sdk.client.YuanApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yuan-api.client")
@Data
@ComponentScan
public class YuanClientConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public YuanApiClient yuanApiClient(){
        return new YuanApiClient(accessKey,secretKey);
    }
}
