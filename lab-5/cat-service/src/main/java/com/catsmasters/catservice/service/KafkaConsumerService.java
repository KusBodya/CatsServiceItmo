
package com.catsmasters.catservice.service;

import com.catsmasters.catservice.dto.kafka.MasterCreatedEvent;
import com.catsmasters.catservice.dto.kafka.MasterDeletedEvent;
import com.catsmasters.catservice.model.Cat;
import com.catsmasters.catservice.repository.CatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final CatRepository catRepository;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(CatRepository catRepository, ObjectMapper objectMapper) {
        this.catRepository = catRepository;
       this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "master.created")
    public void handleMasterCreated(String message) {
        try {
            MasterCreatedEvent event = objectMapper.readValue(message, MasterCreatedEvent.class);
            log.info("Получено событие создания владельца: {}", event.getMasterId());

        } catch (JsonProcessingException e) {
            log.error("Ошибка обработки события master.created: {}", e.getMessage());
       }
   }

    @KafkaListener(topics = "master.deleted")
   public void handleMasterDeleted(String message) {
        try {
            MasterDeletedEvent event = objectMapper.readValue(message, MasterDeletedEvent.class);
           log.info("Получено событие удаления владельца: {}", event.getMasterId());

           List<Cat> cats = catRepository.findByOwnerId(event.getMasterId());
           if (!cats.isEmpty()) {
               cats.forEach(cat -> cat.setOwnerId(null));
               catRepository.saveAll(cats);

               log.info("Обновлено {} котов после удаления владельца {}", cats.size(), event.getMasterId());
           } else {
               log.info("Коты с ownerId={} не найдены", event.getMasterId());
           }
        } catch (JsonProcessingException e) {
           log.error("Ошибка обработки события master.deleted: {}", e.getMessage());
        }
    }
}

