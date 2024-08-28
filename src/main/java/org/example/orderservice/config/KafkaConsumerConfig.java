package org.example.orderservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.orderservice.model.response.SimpleTestClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${myapp.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${myapp.topics.groupId}")
    String groupId;
    private Map<String, Object> createConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return configProps;
    }
    @Bean
    public ConsumerFactory<String, SimpleTestClass> userRegistrationConsumerFactory() {
        Map<String, Object> configProps=createConfig();
        JsonDeserializer<SimpleTestClass> deserializer = new JsonDeserializer<>(SimpleTestClass.class);
        deserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<>(configProps,new StringDeserializer(), deserializer);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, SimpleTestClass> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, SimpleTestClass> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userRegistrationConsumerFactory());
        return factory;
    }

}
