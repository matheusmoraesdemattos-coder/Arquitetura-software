package br.com.mvc.model.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HubClient {

    private final RestClient restClient = RestClient.create();
    private final String hubUrl;

    public HubClient(@Value("${hub.url}") String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public String faturar(Long pedidoId, BigDecimal valor) {
        FaturaResponse resposta = restClient.post()
                .uri(hubUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new FaturaRequest(pedidoId, valor))
                .retrieve()
                .body(FaturaResponse.class);
        return resposta.faturaId();
    }

    public record FaturaRequest(Long pedidoId, BigDecimal valor) {
    }

    public record FaturaResponse(String faturaId) {
    }
}
