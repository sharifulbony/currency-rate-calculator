package com.shariful.kn.task.processess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariful.kn.task.dtos.CurrencyRateLatest;
import com.shariful.kn.task.dtos.CurrencyRateTimeSeries;
import com.shariful.kn.task.dtos.RateResponse;
import com.shariful.kn.task.exceptions.ApiException;
import com.shariful.kn.task.exceptions.InvalidInputException;
import com.shariful.kn.task.exceptions.TechnicalException;
import com.shariful.kn.task.services.WebClientService;
import com.shariful.kn.task.utils.RateCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Currency;
import java.util.Optional;
import java.util.Scanner;


@RequiredArgsConstructor
public class CurrencyRateCalculationProcess {

    public static final Logger LOG = LoggerFactory.getLogger(CurrencyRateCalculationProcess.class);

    public static void main(String[] args){
        try {
            Scanner input = new Scanner(System.in);
            String inputCurrency = input.next().toUpperCase();
            validate(inputCurrency);
            final WebClientService webClientService = new WebClientService();
            Optional<String> timeSeriesRates = webClientService.getTimeSeriesRates(inputCurrency);
            Optional<String> latestRates = webClientService.getLatestRates(inputCurrency);
            if (timeSeriesRates.isPresent() && latestRates.isPresent()) {
                final ObjectMapper objectMapper = new ObjectMapper();
                CurrencyRateTimeSeries currencyRateTimeSeries = objectMapper.readValue(timeSeriesRates.get(),
                        CurrencyRateTimeSeries.class);
                CurrencyRateLatest currencyRateLatest = objectMapper.readValue(latestRates.get(),
                        CurrencyRateLatest.class);

                RateResponse rateResponse = RateCalculator.getRateResponse(currencyRateTimeSeries, currencyRateLatest, inputCurrency);
                LOG.info("Rate response is - {} ", rateResponse);
            } else {
                throw new TechnicalException("Unknown exception occurred while processing response");
            }


        } catch (InvalidInputException | TechnicalException processingException) {
            LOG.warn(processingException.getMessage());
        } catch (WebClientResponseException webClientResponseException) {
            LOG.warn(webClientResponseException.getResponseBodyAsString());
        } catch (JsonProcessingException jsonProcessingException) {
            LOG.warn("Unable to parse response- {}", jsonProcessingException.getMessage());
        } catch (ApiException apiException) {
            LOG.warn("Exception occurred while requesting for response error code -{} , error was -{}",
                    apiException.getStatusCode(), apiException.getResponseBody());
        } catch (Exception exception) {
            LOG.warn("unknown exception occurred details - {}", exception.getMessage());
        }

    }

    private static void validate(String inputCurrency){
        boolean isValidCurrency = Currency.getAvailableCurrencies().stream()
                .anyMatch(currency -> currency.getCurrencyCode().equals(inputCurrency));
        if (!isValidCurrency) {
            throw new InvalidInputException("Illegal input , Currency is not available in currency list " +
                    "or length not correct");
        }
    }
}
