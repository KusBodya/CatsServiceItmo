package com.catsmasters.masterservice.service;

import com.catsmasters.masterservice.dto.MasterDto;
import com.catsmasters.masterservice.dto.kafka.MasterCreatedEvent;
import com.catsmasters.masterservice.dto.kafka.MasterDeletedEvent;
import com.catsmasters.masterservice.model.Master;
import com.catsmasters.masterservice.repository.MasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {

    private static final Logger log = LoggerFactory.getLogger(MasterService.class);

    private final MasterRepository masterRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MasterService(MasterRepository masterRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.masterRepository = masterRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<MasterDto> getAllMasters() {
        return masterRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MasterDto getMasterById(Long id) {
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Master not found with id: " + id));
        System.out.println(master);
        return convertToDto(master);
    }


    public MasterDto createMaster(MasterDto masterDto) {
        Master master = convertToEntity(masterDto);
        Master savedMaster = masterRepository.save(master);

        MasterCreatedEvent event = new MasterCreatedEvent(savedMaster.getId(), savedMaster.getName());
        kafkaTemplate.send("master.created", event);
        log.info("Отправлено событие создания владельца: {}", savedMaster.getId());

        return convertToDto(savedMaster);
    }

    public MasterDto updateMaster(Long id, MasterDto masterDto) {
        Master existingMaster = masterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Master not found with id: " + id));

        existingMaster.setName(masterDto.getName());
        existingMaster.setBirthDate(masterDto.getBirthDate());

        Master updatedMaster = masterRepository.save(existingMaster);
        return convertToDto(updatedMaster);
    }

    public void deleteMaster(Long id) {
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Master not found with id: " + id));

        masterRepository.deleteById(id);

        MasterDeletedEvent event = new MasterDeletedEvent(id);
        kafkaTemplate.send("master.deleted", event);
        log.info("Отправлено событие удаления владельца: {}", id);
    }

    private MasterDto convertToDto(Master master) {
        MasterDto dto = new MasterDto();
        dto.setId(master.getId());
        dto.setName(master.getName());
        dto.setBirthDate(master.getBirthDate());
        return dto;
    }

    private Master convertToEntity(MasterDto dto) {
        Master master = new Master();
        master.setName(dto.getName());
        master.setBirthDate(dto.getBirthDate());
        return master;
    }
}
