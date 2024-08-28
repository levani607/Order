package org.example.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.response.SimpleTestClass;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyKafkaListener {

    @KafkaListener(topics = {"${myapp.topics.name}"})
    public void receiveTestClass(SimpleTestClass someTestClass) {
       log.debug("Received test class: {}", someTestClass.getSomeString());
    }
}
