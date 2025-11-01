package com.catsmasters.gateway.service;

import com.catsmasters.gateway.dto.MasterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterOrchestrationService {

    @Value("${services.cat-service.url:http://localhost:8082}")

    private String masterServiceUrl;

    private final WebClient webClient = WebClient.builder().build();

    public List<MasterDto> getAllMasters() {
        return webClient.get()
                .uri(masterServiceUrl + "/api/masters")
                .retrieve()
                .bodyToFlux(MasterDto.class)
                .collectList()
                .block();
    }

    public MasterDto getMasterById(Long id) {
        return webClient.get()
                .uri(masterServiceUrl + "/api/masters/" + id)
                .retrieve()
                .bodyToMono(MasterDto.class)
                .block();
    }

    public MasterDto createMaster(MasterDto masterDto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonBody = mapper.writeValueAsString(masterDto);

            return webClient.post()
                    .uri(masterServiceUrl + "/api/masters")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonBody)
                    .retrieve()
                    .bodyToMono(MasterDto.class)
                    .block();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize MasterDto", e);
        }
    }

    public MasterDto updateMaster(Long id, MasterDto masterDto) {
        return webClient.put()
                .uri(masterServiceUrl + "/api/masters/" + id)
                .body(Mono.just(masterDto), MasterDto.class)
                .retrieve()
                .bodyToMono(MasterDto.class)
                .block();
    }

    public void deleteMaster(Long id) {
        webClient.delete()
                .uri(masterServiceUrl + "/api/masters/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}