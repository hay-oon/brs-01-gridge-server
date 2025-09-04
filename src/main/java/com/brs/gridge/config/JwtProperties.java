package com.brs.gridge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt") // application.properties에서 jwt 접두사로 시작하는 속성들을 자동으로 바인딩
public class JwtProperties {
    private String secret;
    private long accessTokenValidityInMinutes;
    private long refreshTokenValidityInDays;
}
