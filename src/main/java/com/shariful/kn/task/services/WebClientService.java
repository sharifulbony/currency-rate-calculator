package com.shariful.kn.task.services;

import com.shariful.kn.task.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import static com.shariful.kn.task.utils.Constant.*;

@Service
public class WebClientService {

    private static final Logger LOG = LoggerFactory.getLogger(WebClientService.class);
    private final WebClient webClient;

    public WebClientService() {
        this.webClient = customizeWebClient();
    }

    private WebClient customizeWebClient(){
        return   WebClient.builder()
                .filters(exchangeFilterFunctions -> {
                    if (LOG.isDebugEnabled()) {
                        exchangeFilterFunctions.add(requestFilter());
                        exchangeFilterFunctions.add(responseFilter());
                    }
                })
                .baseUrl(BASE_EXCHANGE_RATE_URL)
                .build();
    }

    private ExchangeFilterFunction requestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            LOG.debug("Sending [{}] request to - {} \nHeaders - {} \nBody - {}",
                    request.method(), request.url(), request.headers(), request.body());
            return Mono.just(request);
        });
    }

    private ExchangeFilterFunction responseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            LOG.debug("Received {} \nHeaders {}", response.statusCode(), response.headers());
            return Mono.just(response);
        });
    }

    public Optional<String> getApiResponse(String path, Function<UriBuilder, URI> uriFunction, HttpHeaders httpHeaders) {
        return getApiResponse(path, uriFunction, httpHeaders, String.class);
    }

    private <T> Optional<T> getApiResponse(String path, Function<UriBuilder, URI> uriFunction,
                                           HttpHeaders headers, Class<T> type) {
        if (uriFunction == null) {
            uriFunction = UriBuilder::build;
        }
        return this.webClient
                .method(HttpMethod.GET)
                .uri(path, uriFunction)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> {
                    if (headers != null) {
                        h.addAll(headers);
                    }
                })
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(response -> new ApiException(clientResponse.rawStatusCode(), response)))
                .toEntity(type)
                .blockOptional()
                .map(HttpEntity::getBody);
    }

    public Optional<String> getTimeSeriesRates(String currency) {
        Function<UriBuilder, URI> uriFunction = uriBuilder -> uriBuilder
                .queryParam(START_DATE_PARAM, LocalDate.now().minusDays(TIME_WINDOW))
                .queryParam(END_DATE_PARAM, LocalDate.now())
                .queryParam(BASE_CURRENCY_PARAM, BITCOIN)
                .queryParam(OUTPUT_CURRENCY_PARAM, currency)
                .build();
        return getApiResponse(TIME_SERIES_RATE_URL, uriFunction, null);
    }
    public Optional<String> getLatestRates(String currency) {
        Function<UriBuilder, URI> uriFunction = uriBuilder -> uriBuilder
                .queryParam(BASE_CURRENCY_PARAM, BITCOIN)
                .queryParam(OUTPUT_CURRENCY_PARAM, currency)
                .build();
        return getApiResponse(LATEST_RATE_URL, uriFunction, null);
    }

}
