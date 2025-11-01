package com.catsmasters.gateway.service;

import com.catsmasters.gateway.dto.CatDto;
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
public class CatOrchestrationService {

    @Value("${services.cat-service.url:http://localhost:8082}")
    private String catServiceUrl;

    private final WebClient webClient = WebClient.builder().build();

    public List<CatDto> getAllCats() {
        return webClient.get()
                .uri(catServiceUrl + "/api/cats")
                .retrieve()
                .bodyToFlux(CatDto.class)
                .collectList()
                .block();
    }

    public CatDto getCatById(Long id) {
        return webClient.get()
                .uri(catServiceUrl + "/api/cats/" + id)
                .retrieve()
                .bodyToMono(CatDto.class)
                .block();
    }

    public CatDto createCat(CatDto catDto) {
        return webClient.post()
                .uri(catServiceUrl + "/api/cats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(catDto)
                .retrieve()
                .bodyToMono(CatDto.class)
                .block();
    }

    public CatDto updateCat(Long id, CatDto catDto) {
        return webClient.put()
                .uri(catServiceUrl + "/api/cats/" + id)
                .body(Mono.just(catDto), CatDto.class)
                .retrieve()
                .bodyToMono(CatDto.class)
                .block();
    }

    public void deleteCat(Long id) {
        webClient.delete()
                .uri(catServiceUrl + "/api/cats/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}