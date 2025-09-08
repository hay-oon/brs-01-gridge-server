package com.brs.gridge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "portone")
public class PortOneProperties {
    
    private String apiKey;
    private String secretKey;
    private String storeId;
    private String channelKey;
    private String environment;
    
}
