package org.example.szsubway.common.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengyadong
 * @date 2022/5/25 16:47
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {

        private String endpoint;

        private String accessKey;

        private String secretKey;

        private String bucket;

        @Bean
        public MinioClient minioClient() {
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        }

    }