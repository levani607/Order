package org.example.orderservice.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class HazelcastConfig {

    @Value("${hazelcast.url}")
    private String hazelcastUrl;

    @Bean
    @Primary
    public HazelcastInstance hazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.getNetworkConfig().addAddress(hazelcastUrl);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    public HazelcastCacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }
}
