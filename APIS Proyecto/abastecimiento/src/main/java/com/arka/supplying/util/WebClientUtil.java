package com.arka.supplying.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WebClientUtil {

    private final WebClient webClient;

    public WebClientUtil(@Value("${cliente.servicio.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     *   endpoint dinámico con parámetros opcionales.
     *
     * @param endpoint Ruta relativa del endpoint (ej. "/clientes/{id}")
     * @param pathVariables Variables de ruta (ej. id)
     * @param queryParams Parámetros de consulta (ej. ?activo=true)
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T> Tipo genérico de respuesta
     * @return Mono con la respuesta
     */
    public <T> Mono<T> get(String endpoint, Object[] pathVariables, Map<String, String> queryParams, Class<T> responseType) {
        return webClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(endpoint);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build(pathVariables);
                })
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Realiza una llamada POST a un endpoint dinámico.
     *
     * @param endpoint Ruta relativa del endpoint
     * @param body Cuerpo de la solicitud
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T> Tipo genérico de respuesta
     * @param <B> Tipo genérico del cuerpo
     * @return Mono con la respuesta
     */
    public <T, B> Mono<T> post(String endpoint, B body, Class<T> responseType) {
        return webClient.post()
                .uri(endpoint)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }
}
